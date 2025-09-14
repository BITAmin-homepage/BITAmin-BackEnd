package BITAmin.BE.project.dto;

import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.enums.Period;

import java.time.LocalDate;

public record ProjectInfoDto(
        Long projectId,
        String title,
        String category,
        String description,
        String cohort,
        Period period,
        Award award,
        String member,
        LocalDate duration
) {
    public Project toEntity(ProjectInfoDto dto) {
        return Project.builder()
                .projectId(this.projectId)
                .title(this.title)
                .category(this.category)
                .description(this.description)
                .cohort(this.cohort)
                .period(this.period)
                .award(this.award)
                .member(this.member)
                .duration(this.duration)
                .build();
    }

    public static ProjectInfoDto fromEntity(Project project) {
        return new ProjectInfoDto(
                project.getProjectId(),
                project.getTitle(),
                project.getCategory(),
                project.getDescription(),
                project.getCohort(),
                project.getPeriod(),
                project.getAward(),
                project.getMember(),
                project.getDuration()
        );
    }
}