package com.haessae0.boilerplate.config;

import com.haessae0.boilerplate.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.haessae0.boilerplate.config
 * fileName       : SecurityConfig
 * author         : haessae0
 * date           : 25. 11. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 11.        haessae0       최초 생성
 */
@Configuration
@EnableWebSecurity
// @RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    // Lombok 사용 시 - @RequiredArgsConstructor 대체
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 세션 관리 설정 - STATELESS로 설정하면 세션 사용 안함
                // 추후 JWT 적용 시 변경 예정
                // API 테스트를 위해 일단 ALWAYS로 설정 (세션 기반)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )

                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 공개 엔드포인트 - OAuth2를 위해 인증 관련 허용
                        .requestMatchers("/", "/api/auth/**", "/oauth2/**", "/login/**").permitAll()
                        // 나머지는 인증 필요
                        .anyRequest().authenticated()
                )

                // OAuth2 설정
                .oauth2Login(oauth2 -> oauth2
                        // 커스텀 OAuth2UserService 등록
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        // 로그인 성공 후 리다이렉트
                        .defaultSuccessUrl("/api/auth/login-success", true)
                        // 로그인 실패 시 리다이렉트
                        .failureUrl("/api/auth/login-failure")
                );

        return http.build();
    }
}
