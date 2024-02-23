package io.goorm.ainfras.target.domain.Authentication.controller;

import io.goorm.ainfras.target.domain.Authentication.service.AuthenticationService;
import io.goorm.ainfras.target.domain.User.dto.LoginRequest;
import io.goorm.ainfras.target.domain.User.dto.SignUpRequest;
import io.goorm.ainfras.target.global.interceptor.LogPrinter;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping("login")
    public String login() {
        return "login";
    }

    /*
    @PostMapping("login")
    public String login(@ModelAttribute LoginRequest request, RedirectAttributes redirectAttributes) {
        try {
            authenticationService.login(request);
            return "redirect:/home";
        } catch (EntityNotFoundException exception) {
            LOGGER.info("[login] 로그인 오류, message: {}", exception.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/login";
        } catch (Exception exception) {
            LOGGER.info("[login] 로그인 오류, message: {}", exception.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "redirect:/login";
        }
    }
     */

    @GetMapping("register")
    public String signup() {
        return "register";
    }

    @PostMapping("register")
    @LogPrinter
    public String register(@ModelAttribute SignUpRequest request, RedirectAttributes redirectAttributes) {
        try {
            authenticationService.save(request);
            return "redirect:/login";
        } catch (EntityExistsException exception) {
            LOGGER.info("[register] 회원가입 오류, message: {} ", exception.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/register";
        } catch (Exception exception) {
            LOGGER.info("[register] 회원가입 오류, message: {} ", exception.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "redirect:/register";
        }
    }
}
