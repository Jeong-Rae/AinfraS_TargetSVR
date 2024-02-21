package io.goorm.ainfras.target.domain.User.dto;

public record RegisterRequest(
        String email,
        String password,
        String nickname
) {
}
