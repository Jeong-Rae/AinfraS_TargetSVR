package io.goorm.ainfras.target.domain.User.dto;

import io.goorm.ainfras.target.domain.User.domain.RoleType;

public record LoginResponse(
    String email,
    String nickname,
    RoleType roleType
) {
}
