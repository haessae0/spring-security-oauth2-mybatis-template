package com.haessae0.boilerplate.mapper;

import com.haessae0.boilerplate.domain.AllowedOrigin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.haessae0.boilerplate.mapper
 * fileName       : AllowedOriginMapper
 * author         : haessae0
 * date           : 25. 11. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 10.        haessae0       최초 생성
 */
@Mapper
public interface AllowedOriginMapper {
    List<AllowedOrigin> findAllOrigins(); // 활성화된 모든 Origin 조회

    AllowedOrigin findByOrigin(String origin); // 특정 Origin이 허용되었는지 확인

    int insertOrigin(AllowedOrigin allowedOrigin); // 새로운 Origin 추가

    int updateOrigin(AllowedOrigin allowedOrigin); // Origin 정보 업데이트

    int deleteOrigin(Long id); // id에 해당하는 Origin 삭제
}
