package BITAmin.BE.project.entity;
import BITAmin.BE.global.generic.CrudEntity;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.enums.Award;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project implements CrudEntity<ProjectInfoDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String title;
    private String category;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String cohort;
    private String period;
    @Enumerated(EnumType.STRING)
    private Award award;
    private String member;
    private LocalDate startDate;
    private LocalDate endDate;
    private String thumbnail;
    private String ppt;

    public void update(ProjectInfoDto dto) {
        this.title = dto.title();
        this.category = dto.category();
        this.description = dto.description();
        this.cohort = dto.cohort() != null ? String.join(", ", dto.cohort()) : this.cohort;
        this.period = dto.period();
        this.award = dto.award();
        this.member = dto.member();
        this.startDate = dto.startDate();
        this.endDate = dto.endDate();
    }
    @Override
    public ProjectInfoDto toDto() {
        return ProjectInfoDto.fromEntity(this);
    }
}
