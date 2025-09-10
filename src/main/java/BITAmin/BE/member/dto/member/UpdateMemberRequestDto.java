package BITAmin.BE.member.dto.member;

import BITAmin.BE.member.enums.Role;

public record UpdateMemberRequestDto(
        String name,
        String gender,
        String birthDate,
        String school,
        String phone,
        String email,
        Long cohort,
        Role role
) {
}
