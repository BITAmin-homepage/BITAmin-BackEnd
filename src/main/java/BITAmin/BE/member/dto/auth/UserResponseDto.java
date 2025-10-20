package BITAmin.BE.member.dto.auth;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.enums.Status;

public record UserResponseDto(
        String id,
        String gender,
        String birthDate,
        String school,
        String phone,
        String name,
        String email,
        Role role,
        Long cohort,
        String token,
        Status status
) {
    public static UserResponseDto from(Member member, String token) {
        return new UserResponseDto(
                String.valueOf(member.getMemberId()),
                member.getGender(),
                member.getBirthDate(),
                member.getSchool(),
                member.getPhone(),
                member.getName(),
                member.getEmail(),
                member.getRole(),
                member.getCohort(),
                token,
                member.getStatus()
        );
    }
}
