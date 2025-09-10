package BITAmin.BE.member.dto.member;

import lombok.Getter;

public record MemberSearchCondition(
        String name,
        String email,
        String school,
        Long cohort
) {}
