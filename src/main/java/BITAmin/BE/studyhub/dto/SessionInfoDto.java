package BITAmin.BE.studyhub.dto;

import BITAmin.BE.studyhub.entity.Session;

public record SessionInfoDto(
        Long sessionId,
        String title,
        String week,
        String description,
        String detail
) {
    // Entity → DTO 변환
    public static SessionInfoDto fromEntity(Session session) {
        return new SessionInfoDto(
                session.getSessionId(),
                session.getTitle(),
                session.getWeek(),
                session.getDescription(),
                session.getDetail()
        );
    }

    // DTO → Entity 변환
    public Session toEntity(SessionInfoDto dto) {
        return Session.builder()
                .sessionId(dto.sessionId)
                .title(dto.title)
                .week(dto.week)
                .description(dto.description)
                .detail(dto.detail)
                .build();
    }
}
