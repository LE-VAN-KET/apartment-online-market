package com.cdcn.apartmentonlinemarket.users.services;

import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;
import com.cdcn.apartmentonlinemarket.users.domain.dto.UserDto;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import com.cdcn.apartmentonlinemarket.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
//    SignupResponse authentication(SignupCommand loginRequest);
//
//
//    Users getCurrentUser();
//
//    Users registerNewUserAccount(RegisterRequest registerRequest) throws UserAlreadyExistException;
//
//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);
//
//    void updateRefreshTokenByUsername(String username);
//
////    TokenRefreshResponse resetPassword(String passwordNew, DeviceMeta deviceMeta);

    List<Users> findAll();

    UserDto findById(String id);

    void deleteById(String id);

    UserDto updateUser(String id, UserDto user);
    public Optional<Users> findUserByEmail(String email);
    public Optional<Users> findUserByResetToken(String resetToken);
    public void save(Users user);
    public Optional<Users> forgotPassword(String email);



}
