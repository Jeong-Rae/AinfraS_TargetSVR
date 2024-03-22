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
    private final String HOST = "a206770186a284a10a33d0dc2643cb0f-640062525.ap-northeast-2.elb.amazonaws.com";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String requestAddr = getOriginRemoteAddr(request);

        CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper(request);
        String body = wrappedRequest.getBody();

        StringBuilder sb = new StringBuilder();


        sb.append("\n").append("[HTTP MESSAGE]").append("\n");
        String queryString = request.getQueryString(); // 쿼리 스트링 가져오기
        // 쿼리 스트링 URI에 추가
        String urlWithQueryString = request.getRequestURI() + (queryString != null ? "?" + queryString : "");

        sb
                .append(request.getMethod()).append(" ")
                .append(HOST).append(urlWithQueryString).append(" ") // 로케이션에 쿼리 추가
                .append(request.getProtocol()).append("\n"); // 프로토콜
        Collections.list(request.getHeaderNames())
                .forEach(headerName ->
                        sb.append(headerName).append(": ")
                                .append(request.getHeader(headerName)).append("\n")
                );

        sb.append("\n").append(body).append("\n");

        sb.append("[/HTTP MESSAGE]").append("\n");

        LOGGER.info(sb.toString());


        LOGGER.info("[ServletFilter] RequestURI: {}, RequestHost: {}", requestURI, requestAddr);
        filterChain.doFilter(wrappedRequest, response);
    }

    private static String getOriginRemoteAddr(HttpServletRequest request) {
        String originAddr = request.getHeader("X-Real-IP");

        if (originAddr == null || originAddr.isBlank()) {
            originAddr = request.getRemoteAddr();
        }

        return originAddr;
    }
}
