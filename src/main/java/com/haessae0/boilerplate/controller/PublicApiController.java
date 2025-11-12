package com.haessae0.boilerplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.haessae0.boilerplate.controller
 * fileName       : PublicApiController
 * author         : haessae0
 * date           : 25. 11. 12.
 * description    : 인증이 필요 없는 공개 엔드포인트 제공
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 12.        haessae0       최초 생성
 */
@RestController
@RequestMapping("/api/public")
public class PublicApiController {

    // CORS 테스트용 Ping 엔드포인트 (GET)
    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "OK");
        response.put("description", "Public API - No authentication required");

        return ResponseEntity.ok(response);
    }

    // CORS 테스트용 Echo 엔드포인트 (POST)
    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(@RequestBody(required = false) Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Echo successful");
        response.put("timestamp", LocalDateTime.now());
        response.put("receivedData", payload != null ? payload : Map.of());

        return ResponseEntity.ok(response);
    }

    // CORS 테스트용 서버 정보 엔드포인트
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
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

    // CORS 테스트용 Headers 엔드포인트
    @GetMapping("/headers")
    public ResponseEntity<Map<String, Object>> headers(@RequestHeader Map<String, String> headers) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Request headers");
        response.put("timestamp", LocalDateTime.now());
        response.put("headers", headers);

        return ResponseEntity.ok(response);
    }
}
