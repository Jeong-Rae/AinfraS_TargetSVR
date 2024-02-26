package io.goorm.ainfras.target.domain.User.controller;

import io.goorm.ainfras.target.domain.User.domain.User;
import io.goorm.ainfras.target.domain.User.dto.UpdateNicknameRequest;
import io.goorm.ainfras.target.domain.User.dto.UpdatePasswordRequest;
import io.goorm.ainfras.target.domain.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("nickname", user.getNickname());
        System.out.println(user);
        LocalDateTime modifiedDate = userService.getModifiedDate(user);
        model.addAttribute("modifiedDate", modifiedDate);
        model.addAttribute("email", user.getEmail());
        return "mypage";
    }

    @PutMapping("/users/nickname")
    @ResponseBody
    public ResponseEntity<Void> updateNickname(@AuthenticationPrincipal User user,
                                               @RequestBody UpdateNicknameRequest request) {
        try {
            userService.updateUserNickname(user, request);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PutMapping("/users/password")
    @ResponseBody
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal User user,
                                               @RequestBody UpdatePasswordRequest request) {
        userService.updateUserPassword(user, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
