package io.goorm.ainfras.target.domain.User.dto;

public record LoginRequest(
        String email,
        String password
) {
}
