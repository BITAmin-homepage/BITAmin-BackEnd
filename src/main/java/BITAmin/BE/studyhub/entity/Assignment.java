package BITAmin.BE.studyhub.entity;

import BITAmin.BE.global.generic.CrudEntity;
import BITAmin.BE.studyhub.dto.AssignInfoDto;
import BITAmin.BE.studyhub.dto.SessionInfoDto;
import BITAmin.BE.studyhub.enums.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment implements CrudEntity<AssignInfoDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignId;

    private String title;
    private String week;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    private LocalDate deadline;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String detail;

    private String url;

    @Override
    public void update(AssignInfoDto dto) {
        this.title = dto.title();
        this.week = dto.week();
        this.type = dto.type();
        this.deadline = dto.deadline();
        this.description = dto.description();
        this.detail = dto.detail();
        this.url = dto.url();
    }

    @Override
    public AssignInfoDto toDto() {
        return new AssignInfoDto(
                assignId,
                title,
                week,
                type,
                deadline,
                description,
                detail,
                url
        );
    }
}