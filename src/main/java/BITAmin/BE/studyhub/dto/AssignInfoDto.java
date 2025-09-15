package BITAmin.BE.studyhub.dto;

import BITAmin.BE.studyhub.entity.Assignment;
import BITAmin.BE.studyhub.enums.TaskType;

import java.time.LocalDate;

public record AssignInfoDto(
        Long assignId,
        String title,
        String week,
        TaskType type,
        LocalDate deadline,
        String description,
        String detail,
        String url
) {
    public static AssignInfoDto fromEntity(Assignment assignment) {
        return new AssignInfoDto(
                assignment.getAssignId(),
                assignment.getTitle(),
                assignment.getWeek(),
                assignment.getType(),
                assignment.getDeadline(),
                assignment.getDescription(),
                assignment.getDetail(),
                assignment.getUrl()
        );
    }

    public static Assignment toEntity(AssignInfoDto dto) {
        return Assignment.builder()
                .assignId(dto.assignId())
                .title(dto.title())
                .week(dto.week())
                .type(dto.type())
                .deadline(dto.deadline())
                .description(dto.description())
                .detail(dto.detail())
                .url(dto.url())
                .build();
    }
}