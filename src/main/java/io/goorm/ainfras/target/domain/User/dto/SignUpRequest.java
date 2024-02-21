package io.goorm.ainfras.target.domain.User.dto;

public record SignUpRequest(
        String email,
        String password,
        String nickname
) {
}
