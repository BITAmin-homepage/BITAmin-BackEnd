package BITAmin.BE.project.dto;

import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public record ProjectInfoDto(
        String title,
        String category,
        String description,
        List<String> cohort,
        LocalDate startDate,
        LocalDate endDate,
        Award award,
        String member,
        String period

) {
    public Project toEntity(ProjectInfoDto dto) {
        return Project.builder()
                .title(this.title)
                .category(this.category)
                .description(this.description)
                .cohort(String.join(", ", this.cohort))
                .period(this.period)
                .award(this.award)
                .member(this.member)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

    public static ProjectInfoDto fromEntity(Project project) {
        List<String> cohorts = Arrays.stream(project.getCohort().split(","))
                .map(String::trim)
                .toList();
        return new ProjectInfoDto(
                project.getTitle(),
                project.getCategory(),
                project.getDescription(),
                cohorts,
                project.getStartDate(),
                project.getEndDate(),
                project.getAward(),
                project.getMember(),
                project.getPeriod()
        );
    }
}