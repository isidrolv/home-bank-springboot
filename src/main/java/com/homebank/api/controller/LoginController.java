package com.homebank.api.controller;

import com.homebank.api.dto.LoginRequest;
import com.homebank.api.dto.LogoutResponse;
import com.homebank.api.entity.UserSession;
import com.homebank.api.service.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserSession> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("LoginController.login() called");
        try {
            return ResponseEntity
                    .ok(
                            loginService.getUserSession(loginRequest)
                    );
        } catch (SQLException e) {
            log.error("Error during user login: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(UserSession.builder()
                            .message(e.getMessage())
                            .isLoggedIn(false)
                            .build());

        }
    }

    @GetMapping(value = "/logout/{sessionId}")
    public ResponseEntity<LogoutResponse> logout(@PathVariable Integer sessionId) {
        log.info("LoginController.logout() called");
        return ResponseEntity.ok(loginService.logout(sessionId));
    }

}
