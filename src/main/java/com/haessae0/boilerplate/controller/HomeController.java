package com.haessae0.boilerplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.haessae0.boilerplate.controller
 * fileName       : HomeController
 * author         : haessae0
 * date           : 25. 11. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 11.        haessae0       최초 생성
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Spring Boot 3 + Security + MyBatis Boilerplate");
        response.put("description", "Google OAuth2 Login + 동적 CORS 관리");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("Login Guide 링크", "/api/auth/guide");
        endpoints.put("Google Login 링크", "/oauth2/authorization/google");
        endpoints.put("현재 사용자 조회", "/api/auth/me");

        response.put("endpoints", endpoints);
        response.put("status", "running");

        return ResponseEntity.ok(response);
    }
}
