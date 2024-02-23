package io.goorm.ainfras.target.global.interceptor;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LogPrinterInterceptor {
    private final Logger LOGGER = LoggerFactory.getLogger(LogPrinterInterceptor.class);
    @Around("@annotation(LogPrinter)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        LOGGER.info("[{}] Parameter: {}", signature.getMethod().getName(), Arrays.toString(joinPoint.getArgs()));

        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            LOGGER.info("[{}] result: {}", signature.getMethod().getName(), result);
        }

        return result;
    }
}
