package com.cdcn.apartmentonlinemarket.users.services.impl;

import com.cdcn.apartmentonlinemarket.users.repository.UserRepository;
import com.cdcn.apartmentonlinemarket.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

}
