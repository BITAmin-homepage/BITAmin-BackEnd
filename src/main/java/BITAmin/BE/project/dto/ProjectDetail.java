package BITAmin.BE.project.dto;

import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public record ProjectDetail(
        Long projectId,
        String title,
        String category,
        String description,
        List<String> cohort,
        String period,
        Award award,
        String member,
        LocalDate startDate,
        LocalDate endDate,
        String ppt,
        String thumbnail
) {
    public static ProjectDetail fromEntity(Project project) {
        List<String> cohorts = project.getCohort() == null ? List.of()
                : Arrays.stream(project.getCohort().split(","))
                .map(String::trim)
                .toList();

        return new ProjectDetail(
                project.getProjectId(),
                project.getTitle(),
                project.getCategory(),
                project.getDescription(),
                cohorts,
                project.getPeriod(),
                project.getAward(),
                project.getMember(),
                project.getStartDate(),
                project.getEndDate(),
                project.getPpt(),
                project.getThumbnail()
        );
    }
}