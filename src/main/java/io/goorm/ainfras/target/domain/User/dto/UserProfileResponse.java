package io.goorm.ainfras.target.domain.User.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record UserProfileResponse(
        String email,
        String nickname,
        LocalDateTime passwordLastUpdateDate
) {
}
