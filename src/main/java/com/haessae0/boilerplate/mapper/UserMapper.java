package com.haessae0.boilerplate.mapper;

import com.haessae0.boilerplate.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.haessae0.boilerplate.mapper
 * fileName       : UserMapper
 * author         : haessae0
 * date           : 25. 11. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 10.        haessae0       최초 생성
 */
@Mapper
public interface UserMapper {

    User findByEmail(@Param("email") String email); // 이메일로 사용자 조회

    int insertUser(User user); // 새로운 사용자 생성

    int updateUser(User user); // 사용자 정보 업데이트
}
