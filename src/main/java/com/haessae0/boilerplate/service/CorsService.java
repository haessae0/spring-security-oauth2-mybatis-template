package com.haessae0.boilerplate.service;

import com.haessae0.boilerplate.domain.AllowedOrigin;
import com.haessae0.boilerplate.mapper.AllowedOriginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.haessae0.boilerplate.service
 * fileName       : CorsService
 * author         : haessae0
 * date           : 25. 11. 12.
 * description    : CORS 관리 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 12.        haessae0       최초 생성
 */
@Service
// @Slf4j
// @RequiredArgsConstructor
public class CorsService {

    // Lombok 사용 시 - @Slf4j 대체
    private static final Logger logger = LoggerFactory.getLogger(CorsService.class);

    private final AllowedOriginMapper allowedOriginMapper;

    // Lombok 사용 시 - @RequiredArgsConstructor 대체
    public CorsService(AllowedOriginMapper allowedOriginMapper) {
        this.allowedOriginMapper = allowedOriginMapper;
    }

    // 활성화된 모든 Origin 목록 조회
    @Cacheable(value = "allowedOrigins", unless = "#result == null || #result.isEmpty()")
    public List<AllowedOrigin> getAllowedOrigins() {
        logger.info("[+] 허용된 Origin 데이터 목록 조회... (캐시 만료 혹은 제거된 경우에도 동작)");
        List<AllowedOrigin> origins = allowedOriginMapper.findAllOrigins();
        logger.info("[+] 허용된 Origin 개수: {}", origins.size());
        return origins;
    }
}
