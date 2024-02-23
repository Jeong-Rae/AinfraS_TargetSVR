package io.goorm.ainfras.target.domain.Authentication.service;

import io.goorm.ainfras.target.domain.User.service.CustomUserDetailsService;
import io.goorm.ainfras.target.global.interceptor.LogPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    @Override
    @LogPrinter
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("올바르지 않은 인증 정보입니다. user: " + username);
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
