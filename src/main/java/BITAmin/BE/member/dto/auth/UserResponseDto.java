package BITAmin.BE.member.dto.auth;

import BITAmin.BE.member.entity.Member;

public record UserResponseDto(
        String id,
        String name,
        String email,
        String role,   // "member" 또는 "management"
        Long cohort,
        String token
) {
    public static UserResponseDto from(Member member, String token) {
        return new UserResponseDto(
                String.valueOf(member.getMemberId()),
                member.getName(),
                member.getEmail(),
                member.getRole().name(),
                member.getCohort(),
                token
        );
    }
}
