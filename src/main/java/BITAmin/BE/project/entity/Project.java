package BITAmin.BE.project.entity;

import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.enums.Period;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Builder
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String title;
    private String category;
    private String description;
    private String cohort;
    private Period period;
    private Award award;
    private String member;
    private LocalDate duration;

    public void update(ProjectInfoDto dto) {
        this.title = dto.title();
        this.category = dto.category();
        this.description = dto.description();
        this.cohort = dto.cohort();
        this.period = dto.period();
        this.award = dto.award();
        this.member = dto.member();
        this.duration = dto.duration();
    }
}
