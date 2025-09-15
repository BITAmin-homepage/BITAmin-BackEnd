package BITAmin.BE.member.dto.auth;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.enums.Status;

public record UserResponseDto(
        String id,
        String name,
        String email,
        Role role,   // "member" 또는 "management"
        Long cohort,
        String token,
        Status status
) {
    public static UserResponseDto from(Member member, String token) {
        return new UserResponseDto(
                String.valueOf(member.getMemberId()),
                member.getName(),
                member.getEmail(),
                member.getRole(),
                member.getCohort(),
                token,
                member.getStatus()
        );
    }
}
