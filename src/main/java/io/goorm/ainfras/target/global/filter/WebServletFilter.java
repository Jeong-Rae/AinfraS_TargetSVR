package io.goorm.ainfras.target.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@WebFilter(urlPatterns = "/*")
public class WebServletFilter implements Filter {
    private final Logger LOGGER = LoggerFactory.getLogger(WebServletFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("[WebServletFilter] 필터 초기화");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            RequestBodyWrapper requestWrapper = new RequestBodyWrapper(req);
            chain.doFilter(requestWrapper, res);
        } catch (Exception exception) {
            // 디코딩 불가 예외 처리
            LOGGER.error(exception.getMessage());
            LOGGER.error("[WebServletFilter] body 출력 불가");
            String ResponseMessage = "ERROR";
            byte[] data = ResponseMessage.getBytes(StandardCharsets.UTF_8);
            int count = data.length;
            res.setStatus(400);
            res.setContentLength(count);
            res.getOutputStream().write(data);
            res.flushBuffer();
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("[WebServletFilter] 필터 종료");
    }
}