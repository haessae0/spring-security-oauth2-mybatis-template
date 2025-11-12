package com.haessae0.boilerplate.config;

import com.haessae0.boilerplate.domain.AllowedOrigin;
import com.haessae0.boilerplate.service.CorsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * packageName    : com.haessae0.boilerplate.config
 * fileName       : DatabaseCorsConfigurationSource
 * author         : haessae0
 * date           : 25. 11. 12.
 * description    : DB 기반 동적 CORS 및 캐시 적용
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 12.        haessae0       최초 생성
 */
@Component
// @RequiredArgsConstructor
public class DatabaseCorsConfigurationSource implements CorsConfigurationSource {
    private final CorsService corsService;

    // Lombok 사용 시 - @RequiredArgsConstructor 대체
    public DatabaseCorsConfigurationSource(CorsService corsService) {
        this.corsService = corsService;
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        String requestOrigin = request.getHeader("Origin"); // Origin 헤더 추출

        if (requestOrigin == null) return null; // Origin 동일 출처 요청

        // CorsService를 통해 캐시된 허용 Origin 목록 조회
        // 첫 번째 요청 시 DB 조회 이후 요청은 캐시에서 조회
        List<AllowedOrigin> allowedOrigins = corsService.getAllowedOrigins();

        // DB 목록에서 origin 문자열만 추출
        List<String> allowedOriginStrings = allowedOrigins.stream()
                .map(AllowedOrigin::getOrigin)
                .toList();

        // 요청 Origin이 허용 목록에 있는지 확인
        boolean isAllowed = allowedOriginStrings.contains(requestOrigin);

        if (!isAllowed) return null; // 브라우저가 CORS 에러 발생

        // CORS 설정 객체 생성
        CorsConfiguration config = new CorsConfiguration();

        // 허용할 Origin 설정 (요청된 Origin만 허용)
        config.setAllowedOrigins(List.of(requestOrigin));

        // 허용할 HTTP 메서드 설정
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 허용할 헤더 설정
        config.setAllowedHeaders(List.of("*"));

        // 인증 정보(쿠키, Authorization 헤더 등) 포함 허용
        config.setAllowCredentials(true);

        // preflight 요청 캐시 시간 (초 단위)
        config.setMaxAge(3600L);

        return config;
    }
}
