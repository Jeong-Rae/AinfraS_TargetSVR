package io.goorm.ainfras.target.domain.User.domain;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("ROLE_USER"),
    ADMIN("ROME_ADMIN");

    private final String code;

    RoleType(String code) {
        this.code = code;
    }
}
