package io.goorm.ainfras.target.domain.User.service;

import io.goorm.ainfras.target.domain.User.dto.LoginRequest;
import io.goorm.ainfras.target.domain.User.dto.LoginResponse;
import io.goorm.ainfras.target.domain.User.dto.SignUpRequest;
import io.goorm.ainfras.target.domain.User.dto.SignUpResponse;
import io.goorm.ainfras.target.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public SignUpResponse saveUser(SignUpRequest request) {
        return userDetailsService.saveUser(request);
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        return userDetailsService.loginUser(request);
    }

}
