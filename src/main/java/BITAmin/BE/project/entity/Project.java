package BITAmin.BE.project.entity;

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
}
