package io.goorm.ainfras.target.domain.Authentication.service;

import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.LoginRequest;
import io.goorm.ainfras.target.domain.User.dto.LoginResponse;
import io.goorm.ainfras.target.domain.User.dto.SignUpRequest;
import io.goorm.ainfras.target.domain.User.dto.SignUpResponse;
import io.goorm.ainfras.target.domain.User.service.UserService;
import io.goorm.ainfras.target.global.interceptor.LogPrinter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @LogPrinter
    public SignUpResponse save(SignUpRequest request) {
        return userService.saveUser(request);
    }
}
