package io.goorm.ainfras.target.domain.User.converter;

import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convert (RegisterRequest request) {
        return User.builder()
                .email(request.email())
                .nickname(request.nickname())
                .build();
    }
}
