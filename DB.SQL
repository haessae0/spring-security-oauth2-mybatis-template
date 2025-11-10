-- ===================================================================
-- Spring Boot 3 + Security 보일러플레이트 DDL (PostgreSQL)
-- ===================================================================
-- 해당 SQL 순서대로 실행해주세요.

-- updated_at 자동 갱신을 위한 트리거 함수
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 1. users 테이블 (소셜 로그인 사용자 정보)
CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,
   email VARCHAR(255) NOT NULL UNIQUE,
   name VARCHAR(100),
   profile_image_url TEXT,
   provider VARCHAR(50) NOT NULL DEFAULT 'google',
   provider_id VARCHAR(255) NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT uk_users_provider_provider_id UNIQUE (provider, provider_id)
);

--  users 테이블 updated_at 자동 갱신 트리거
CREATE TRIGGER set_timestamp
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION trigger_set_timestamp();

-- users 테이블 코멘트
COMMENT ON TABLE users IS '소셜 로그인 사용자 정보';
COMMENT ON COLUMN users.id IS '사용자 고유 ID';
COMMENT ON COLUMN users.email IS '사용자 이메일 (고유)';
COMMENT ON COLUMN users.name IS '사용자 이름';
COMMENT ON COLUMN users.profile_image_url IS '프로필 이미지 URL';
COMMENT ON COLUMN users.provider IS 'OAuth 제공자 (google, naver, kakao 등)';
COMMENT ON COLUMN users.provider_id IS 'OAuth 제공자의 사용자 고유 ID';
COMMENT ON COLUMN users.created_at IS '생성 일시';
COMMENT ON COLUMN users.updated_at IS '수정 일시';


-- 2. allowed_origins 테이블 (CORS 허용 목록)
CREATE TABLE allowed_origins (
    id BIGSERIAL PRIMARY KEY,
    origin VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_allowed_origins_active_origins
    ON allowed_origins(origin)
    WHERE is_active = true;

-- allowed_origins 테이블 updated_at 자동 갱신 트리거
CREATE TRIGGER set_timestamp
    BEFORE UPDATE ON allowed_origins
    FOR EACH ROW
    EXECUTE FUNCTION trigger_set_timestamp();

-- allowed_origins 테이블 코멘트
COMMENT ON TABLE allowed_origins IS 'CORS 허용 Origin 목록';
COMMENT ON COLUMN allowed_origins.id IS 'Origin 고유 ID';
COMMENT ON COLUMN allowed_origins.origin IS '허용할 Origin URL (예: http://localhost:3000)';
COMMENT ON COLUMN allowed_origins.description IS 'Origin 설명 (어떤 프론트엔드인지 등)';
COMMENT ON COLUMN allowed_origins.is_active IS '활성화 여부 (true: 허용, false: 차단)';
COMMENT ON COLUMN allowed_origins.created_at IS '생성 일시';
COMMENT ON COLUMN allowed_origins.updated_at IS '수정 일시';

-- 3. 샘플 데이터 삽입
INSERT INTO allowed_origins (origin, description, is_active) VALUES
('http://localhost:3000', 'Local Development - React', true),
('http://localhost:8080', 'Local Development - Vue', true),
('http://localhost:5173', 'Local Development - Vite', true);