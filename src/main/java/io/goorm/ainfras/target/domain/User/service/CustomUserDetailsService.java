package io.goorm.ainfras.target.domain.User.service;

import io.goorm.ainfras.target.domain.User.converter.UserConverter;
import io.goorm.ainfras.target.domain.User.domain.RoleType;
import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.LoginRequest;
import io.goorm.ainfras.target.domain.User.dto.LoginResponse;
import io.goorm.ainfras.target.domain.User.dto.SignUpRequest;
import io.goorm.ainfras.target.domain.User.dto.SignUpResponse;
import io.goorm.ainfras.target.domain.User.repository.UserRepository;
import io.goorm.ainfras.target.global.interceptor.LogMonitoring;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserConverter userConverter;

    @Override
    @Transactional
    @LogMonitoring
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username)
                .orElseThrow(() -> {
                    LOGGER.info("[loadUserByUsername] 등록되지 않은 사용자입니다. 사용자: {}", username);
                    return new UsernameNotFoundException("등록되지 않은 사용자입니다.");
                });
        LOGGER.info("[loadUserByUsername] 사용자 조회: {}", user.getEmail());
        return user;
    }

    @Transactional
    @LogMonitoring
    public SignUpResponse saveUser(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            LOGGER.info("[saveUser] 이미 사용중인 이메일, email: {}", request.email());
            throw new EntityExistsException("이미 사용중인 이메일입니다.");
        }

        if (userRepository.existsByNickname(request.nickname())) {
            LOGGER.info("[saveUser] 이미 사용중인 닉네임, nickname: {}", request.nickname());
            throw new EntityExistsException("이미 사용중인 닉네임입니다.");
        }

        User user = userConverter.convert(request);
        user.updateRole(RoleType.USER);
        user.updatePassword(request.password());
        user = userRepository.save(user);
        LOGGER.info("[saveUser] 신규 사용자 등록, email: {}", request.email());

        return userConverter.convert(user, SignUpResponse.class);
    }

    @Transactional
    @LogMonitoring
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    LOGGER.info("[loginUser] 등록되지 않은 사용자, email: {}", request.email());
                    return new EntityNotFoundException("이메일 또는 비밀번호가 잘못되었습니다.");
                });

        if (!user.checkPassword(request.password())) {
            LOGGER.info("[loginUser] 비밀번호가 틀렸습니다. email: {}", request.email());
            throw new EntityNotFoundException("이메일 또는 비밀번호가 잘못되었습니다");
        }

        return userConverter.convert(user, LoginResponse.class);
    }
}
