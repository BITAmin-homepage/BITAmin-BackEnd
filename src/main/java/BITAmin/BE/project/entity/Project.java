package BITAmin.BE.project.entity;
import BITAmin.BE.global.generic.CrudEntity;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.enums.Period;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project implements CrudEntity<ProjectInfoDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String title;
    private String category;
    private String description;
    private String cohort;
    private Period period;
    @Enumerated(EnumType.STRING)
    private Award award;
    private String member;
    private LocalDate startDate;
    private LocalDate endDate;

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
