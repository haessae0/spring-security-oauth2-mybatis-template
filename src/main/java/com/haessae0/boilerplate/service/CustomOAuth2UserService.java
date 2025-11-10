package com.haessae0.boilerplate.service;

import com.haessae0.boilerplate.domain.User;
import com.haessae0.boilerplate.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * packageName    : com.haessae0.boilerplate.service
 * fileName       : CustomOAuth2UserService
 * author         : haessae0
 * date           : 25. 11. 11.
 * description    : OAuth2 로그인 시 사용자 정보를 처리하는 커스텀 서비스, 현재 Google만 지원
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 11.        haessae0       최초 생성
 */
@Service
// @Slf4j
// @RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // Lombok 사용 시 - @Slf4j 대체
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final UserMapper userMapper;

    // Lombok 사용 시 - @RequiredArgsConstructor 대체
    public CustomOAuth2UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 loadUser 호출하여 정보를 가져온다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 provider 추출
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        logger.info("[+] OAuth2 Provider: {}", registrationId);

        // User 정보 추출
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String profileImageUrl = oAuth2User.getAttribute("picture");
        String providerId = oAuth2User.getAttribute("sub"); // Google의 고유 ID

        logger.info("[+] OAuth2 User 정보 - Email: {}, Name: {}, Provider: {}", email, name, registrationId);

        // DB에서 기존 사용자 조회
        User existingUser = userMapper.findByEmail(email);

        if (existingUser == null) {
            // 신규 사용자 -> DB에 저장
            logger.info("[+] 신규 사용자: {}", email);

            // 추후 해당 부분 @Builder 형식으로 변경 가능
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProfileImageUrl(profileImageUrl);
            newUser.setProvider(registrationId);
            newUser.setProviderId(providerId);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setUpdatedAt(LocalDateTime.now());

            int result = userMapper.insertUser(newUser);
            if (result > 0) {
                logger.info("[+] 사용자 회원가입 완료: {}", email);
            } else {
                logger.error("[-] 사용자 회원가입 실패: {}", email);
            }
        } else {
            // 기존 사용자 -> 정보 업데이트
            logger.info("[+] 기사용자 업데이트 진행: {}", email);

            existingUser.setName(name);
            existingUser.setProfileImageUrl(profileImageUrl);
            existingUser.setUpdatedAt(LocalDateTime.now());

            int result = userMapper.updateUser(existingUser);
            if (result > 0) {
                logger.info("[+] 기사용자 업데이트 완료: {}", email);
            }
        }
        return oAuth2User;
    }
}
