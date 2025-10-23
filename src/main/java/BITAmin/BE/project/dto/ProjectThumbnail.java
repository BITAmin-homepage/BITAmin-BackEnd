package BITAmin.BE.project.dto;

import BITAmin.BE.project.enums.Award;

import java.util.List;

public record ProjectThumbnail(
        String thumbnail,
        String ppt,
        String title,
        List<String> cohort,
        String category,
        String period,
        String member,
        Award award

) {}
