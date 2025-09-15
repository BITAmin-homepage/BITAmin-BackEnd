package BITAmin.BE.studyhub.entity;

import BITAmin.BE.global.generic.CrudEntity;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.studyhub.dto.SessionInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session implements CrudEntity<SessionInfoDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    private String title;
    private String week;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String detail;
    @Override
    public void update(SessionInfoDto dto) {
        this.title = dto.title();
        this.week = dto.week();
        this.description = dto.description();
        this.detail = dto.detail();
    }
    @Override
    public SessionInfoDto toDto() {
        return new SessionInfoDto(sessionId, title, week, description, detail);
    }
}
