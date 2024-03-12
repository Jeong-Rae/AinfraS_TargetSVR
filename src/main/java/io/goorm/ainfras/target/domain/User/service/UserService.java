package io.goorm.ainfras.target.domain.User.service;

import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.*;
import io.goorm.ainfras.target.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public SignUpResponse saveUser(SignUpRequest request) {
        return userDetailsService.saveUser(request);
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        return userDetailsService.loginUser(request);
    }

    @Transactional
    public UserProfileResponse getUserProfile(User user) {
        return new UserProfileResponse(user.getEmail(), user.getNickname(), user.getModifiedDate());
    }

    @Transactional
    public void updateUserNickname(User user, UpdateNicknameRequest request) {
        String raw = user.getNickname();
        if (userRepository.existsByNickname(request.nickname())) {
            LOGGER.info("[updateUserNickname] Message: 이미 사용중인 닉네임입니다. {} -> {},", raw, request.nickname());
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
        user.updateNickname(request.nickname());
        userRepository.save(user);
        LOGGER.info("[updateUserNickname] Message: 닉네임이 변경되었습니다. {} -> {},", raw, request.nickname());
    }

    @Transactional
    public void updateUserPassword(User user, UpdatePasswordRequest request) {
        String raw = user.getNickname();
        user.updateNickname(request.password());
        userRepository.save(user);
        LOGGER.info("[updateUserNickname] Message: 비밀번호가 변경되었습니다.");
    }

    @Transactional
    public LocalDateTime getModifiedDate(User user) {
        return userRepository.findModifiedDate(user.getId());
    }

}
