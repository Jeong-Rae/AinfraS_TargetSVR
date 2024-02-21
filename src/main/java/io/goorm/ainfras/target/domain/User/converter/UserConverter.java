package io.goorm.ainfras.target.domain.User.converter;

import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.LoginRequest;
import io.goorm.ainfras.target.domain.User.dto.LoginResponse;
import io.goorm.ainfras.target.domain.User.dto.SignUpRequest;
import io.goorm.ainfras.target.domain.User.dto.SignUpResponse;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convert(SignUpRequest request) {
        return User.builder()
                .email(request.email())
                .nickname(request.nickname())
                .build();
    }

    public <T> T convert(User user, Class<T> clazz) {
        if (clazz.isAssignableFrom(SignUpResponse.class)) {
            return clazz.cast(new SignUpResponse(user.getEmail(), user.getNickname()));
        } else if (clazz.isAssignableFrom(LoginResponse.class)) {
            return clazz.cast(new LoginResponse(user.getEmail(), user.getNickname(), user.getRole()));
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
    }
}
