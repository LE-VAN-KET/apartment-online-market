package com.cdcn.apartmentonlinemarket.security.service;

import com.cdcn.apartmentonlinemarket.common.enums.UserStatus;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Users;
import com.cdcn.apartmentonlinemarket.exception.UserNotFoundException;
import com.cdcn.apartmentonlinemarket.users.repository.UserRepository;
import com.cdcn.apartmentonlinemarket.security.model.CustomUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUserPrincipal loadUserByUsername(String username) {
        Users user = userRepository.findJoinRoleByUsernameEqualsIgnoreCaseAndIsDeleteAndStatus(username,
                        false, UserStatus.ACTIVE)
                .orElseThrow(() -> new UserNotFoundException("Username is not found!"));

        Collection<? extends GrantedAuthority> authorities = getAuthorities(user);

        return new CustomUserPrincipal(username,
                user.getPassword(),
                user.getId().toString(),
                "",
                true,
                authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Users user) {
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return authorities;
    }
}
