package io.goorm.ainfras.target.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class ServletFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(ServletFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String requestAddr = getOriginRemoteAddr(request);

        StringBuilder sb = new StringBuilder();


        sb.append("\n").append("[HTTP MESSAGE]").append("\n");
        sb.append(request.getMethod()).append(" ")
                .append(request.getRequestURI()).append(" ")
                .append(request.getProtocol()).append("\n");
        Collections.list(request.getHeaderNames())
                .forEach(headerName ->
                        sb.append(headerName).append(": ")
                                .append(request.getHeader(headerName)).append("\n")
                );
        sb.append("[/HTTP MESSAGE]").append("\n");

        LOGGER.info(sb.toString());


        LOGGER.info("[ServletFilter] RequestURI: {}, RequestHost: {}", requestURI, requestAddr);
        filterChain.doFilter(request, response);
    }

    private static String getOriginRemoteAddr(HttpServletRequest request) {
        String originAddr = request.getHeader("X-Real-IP");

        if (originAddr == null || originAddr.isBlank()) {
            originAddr = request.getRemoteAddr();
        }

        return originAddr;
    }
}
