package io.goorm.ainfras.target.global.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메서드 레벨에서만 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPrinter {
}
