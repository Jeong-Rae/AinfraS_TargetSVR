package io.goorm.ainfras.target.domain.Authentication.service;

import io.goorm.ainfras.target.domain.User.dto.SignUpRequest;
import io.goorm.ainfras.target.domain.User.dto.SignUpResponse;
import io.goorm.ainfras.target.domain.User.service.UserService;
import io.goorm.ainfras.target.global.interceptor.LogMonitoring;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @LogMonitoring
    public SignUpResponse save(SignUpRequest request) {
        return userService.saveUser(request);
    }
}
