package com.cdcn.apartmentonlinemarket.auth.service;

import com.cdcn.apartmentonlinemarket.auth.dto.mapper.UserMapper;
import com.cdcn.apartmentonlinemarket.auth.dto.request.RefreshTokenRequest;
import com.cdcn.apartmentonlinemarket.auth.dto.request.SigninRequest;
import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SigninResponse;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;
import com.cdcn.apartmentonlinemarket.common.enums.RoleNameDefault;
import com.cdcn.apartmentonlinemarket.configuration.properties.TokenProperties;
import com.cdcn.apartmentonlinemarket.exception.*;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.RedisTokenRepository;
import com.cdcn.apartmentonlinemarket.security.jwt.TokenCreator;
import com.cdcn.apartmentonlinemarket.security.jwt.TokenProvider;
import com.cdcn.apartmentonlinemarket.security.model.TokenPair;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Roles;
import com.cdcn.apartmentonlinemarket.users.domain.entity.UserInformation;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import com.cdcn.apartmentonlinemarket.users.repository.RoleRepository;
import com.cdcn.apartmentonlinemarket.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenCreator tokenCreator;
    private final RedisTokenRepository redisTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProperties tokenProperties;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public SignupResponse signUp(SignupCommand command) {
        validateSignupCommand(command);
        Roles role =  roleRepository.findByName(RoleNameDefault.USER.name()).orElseThrow(() ->
                new RoleNotFoundException("Role not found with name " + RoleNameDefault.USER.name()));
        Users savedUser = createAccount(command, role);
        return SignupResponse.builder().userId(savedUser.getId())
                .username(savedUser.getUsername())
                .message("User register successfully!")
                .build();
    }

    @Override
    public SignupResponse signUpSeller(SignupCommand command) {
        Roles role =  roleRepository.findByName(RoleNameDefault.SELLER.name()).orElseThrow(() ->
                new RoleNotFoundException("Role not found with name " + RoleNameDefault.SELLER.name()));
        Users savedUser = createAccount(command, role);
        return SignupResponse.builder().userId(savedUser.getId())
                .username(savedUser.getUsername())
                .message("User register successfully!")
                .build();
    }

    @Override
    @Transactional
    public SigninResponse signIn(SigninRequest request) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            TokenPair tokenPair = createAndSaveToken(authentication);
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return SigninResponse.builder()
                    .accessToken(tokenPair.getAccessToken())
                    .expiresIn(tokenProperties.getAccessTokenValidityInSeconds())
                    .refreshToken(tokenPair.getRefreshToken())
                    .refreshExpiresIn(tokenProperties.getRefreshTokenValidityInSeconds())
                    .roles(roles)
                    .tokenType("Bearer").build();
        } catch (UserNotFoundException exception) {
            throw new UserNotFoundException(exception.getMessage());
        } catch (BadCredentialsException | InternalAuthenticationServiceException badCredentialsException) {
            log.error("AuthenticationError: {}, cause: {}", badCredentialsException.getMessage(),
                    badCredentialsException.getCause());
            throw new UserBadCredentialsException("Incorrect Username or password!.");
        }
    }

    @Override
    public TokenPair refreshToken(RefreshTokenRequest refreshTokenRequest) {
        var refreshToken = refreshTokenRequest.getToken().trim();
        tokenProvider.validateToken(refreshToken);
        Authentication authentication = tokenProvider.parseAuthentication(refreshToken);
        TokenPair currentTokenPair = redisTokenRepository.read(authentication.getName());

        if (currentTokenPair == null || !Objects.equals(refreshToken, currentTokenPair.getRefreshToken()))
            throw new InvalidRefreshTokenException(400, "Invalid refresh token!");
        return createAndUpdateToken(authentication);
    }

    private TokenPair createAndSaveToken(Authentication authentication) {
        TokenPair tokenPair = tokenCreator.createTokenPair(authentication);
        redisTokenRepository.save(authentication.getName(), tokenPair);
        return tokenPair;
    }

    private TokenPair createAndUpdateToken(Authentication authentication) {
        TokenPair tokenPair = tokenCreator.createTokenPair(authentication);
        redisTokenRepository.update(authentication.getName(), tokenPair);
        return tokenPair;
    }

    private void validateSignupCommand(SignupCommand command) {
        boolean existUsername = userRepository.existsByUsername(command.getUsername());
        if (existUsername) {
            throw new UsernameAlreadyExist();
        }
        boolean existEmail = userRepository.existsByMailNotification(command.getMailNotification());
        if (existEmail) {
            throw new EmailAlreadyExist();
        }
    }

    private Users createAccount(SignupCommand command, Roles role) {
        Users user = userMapper.convertSignupCommandToUser(command);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserInformation userInformation = new UserInformation();
        userInformation.setLastLogin(Instant.now());
        user.setUserInformation(userInformation);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
