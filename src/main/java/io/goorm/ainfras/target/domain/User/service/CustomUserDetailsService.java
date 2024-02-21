package io.goorm.ainfras.target.domain.User.service;

import io.goorm.ainfras.target.domain.User.converter.UserConverter;
import io.goorm.ainfras.target.domain.User.domain.RoleType;
import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.RegisterRequest;
import io.goorm.ainfras.target.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserConverter userConverter;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username)
                .orElseThrow(() -> {
                    LOGGER.info("[loadUserByUsername] 등록되지 않은 사용자입니다. 사용자: {}", username);
                    return new UsernameNotFoundException("등록되지 않은 사용자입니다.");
                });
        LOGGER.info("[loadUserByUsername] 사용자 조회: {}", user.getEmail());
        return user;
    }

    public User saveUser(RegisterRequest request) {
        User user = userConverter.convert(request);
        user.updateRole(RoleType.USER);
        user.updatePassword(request.password());
        userRepository.save(user);
        return user;
    }
}
