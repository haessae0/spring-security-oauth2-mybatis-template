package com.haessae0.boilerplate.controller;

import com.haessae0.boilerplate.domain.User;
import com.haessae0.boilerplate.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.haessae0.boilerplate.controller
 * fileName       : AuthController
 * author         : haessae0
 * date           : 25. 11. 11.
 * description    : 인증 관련 REST API 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 11.        haessae0       최초 생성
 */
@RestController
@RequestMapping("/api/auth")
// @Slf4j
// @RequiredArgsConstructor
public class AuthController {

    // Lombok 사용 시 - @Slf4j 대체
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserMapper userMapper;

    // Lombok 사용 시 - @RequiredArgsConstructor 대체
    public AuthController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 현재 로그인한 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            logger.warn("[-] 허용되지 않은 접근입니다.: /api/auth/me");
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        String email = oAuth2User.getAttribute("email");
        logger.info("[+] 유저 정보 가져오기 완료: {}", email);

        User user = userMapper.findByEmail(email); // DB에서 사용자 정보 조회

        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("profileImageUrl", user.getProfileImageUrl());
            response.put("provider", user.getProvider());
            response.put("createdAt", user.getCreatedAt());
            response.put("updatedAt", user.getUpdatedAt());
        } else {
            // OAuth2User는 있지만 DB에는 없는 경우
            response.put("email", oAuth2User.getAttribute("email"));
            response.put("name", oAuth2User.getAttribute("name"));
            response.put("profileImageUrl", oAuth2User.getAttribute("picture"));
            response.put("message", "데이터베이스에서 사용자 정보를 가져올 수 없습니다.");
        }

        return ResponseEntity.ok(response);
    }

    // OAuth2 로그인 성공 후 리디렉션 엔드포인트
    @GetMapping("/login-success")
    public ResponseEntity<Map<String, Object>> loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User) {
        logger.info("[+] 로그인 성공: " + oAuth2User.getAttribute("email"));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("email", oAuth2User.getAttribute("email"));
        response.put("name", oAuth2User.getAttribute("name"));

        return ResponseEntity.ok(response);
    }

    // OAuth2 로그인 실패 시 리디렉션 엔드포인트
    @GetMapping("/login-failure")
    public ResponseEntity<Map<String, String>> loginFailure() {
        logger.error("[-] 로그인 실패");

        Map<String, String> response = new HashMap<>();
        response.put("error", "Login failed");
        response.put("message", "Google OAuth2 authentication failed");

        return ResponseEntity.status(401).body(response);
    }

    // 로그인 가이드 엔드포인트
    @GetMapping("/guide")
    public ResponseEntity<Map<String, String>> loginGuide() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Google OAuth2 Login Boilerplate");
        response.put("loginUrl", "/oauth2/authorization/google");
        response.put("description", "Google OAuth2 Login 과정을 진행합니다.");

        return ResponseEntity.ok(response);
    }

}