package com.haessae0.boilerplate.domain;

import java.time.LocalDateTime;

/**
 * packageName    : com.haessae0.boilerplate.domain
 * fileName       : AllowedOrigin
 * author         : haessae0
 * date           : 25. 11. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 11. 10.        haessae0       최초 생성
 */
// @NoArgsConstructor
// @AllArgsConstructor
// @Getter
// @Setter
// @ToString
public class AllowedOrigin {
    private Long id;
    private String origin;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Lombok 사용시 - @NoArgsConstructor 대체
    public AllowedOrigin() {
    }

    // Lombok 사용시 - @AllArgsConstructor 대체
    public AllowedOrigin(Long id, String origin, String description, Boolean isActive,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.origin = origin;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Lombok 사용시 - @Getter, @Setter 대체
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Lombok 사용시 - @ToString 대체
    @Override
    public String toString() {
        return "AllowedOrigin{" +
                "id=" + id +
                ", origin='" + origin + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
