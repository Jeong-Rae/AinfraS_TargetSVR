package io.goorm.ainfras.target;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@ServletComponentScan
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class TargetSvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(TargetSvrApplication.class, args);
    }

}
