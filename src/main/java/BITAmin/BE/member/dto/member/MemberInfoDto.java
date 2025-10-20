package BITAmin.BE.member.dto.member;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.enums.Status;


public record MemberInfoDto(
        Long memberId,
        String name,
        String email,
        String school,
        Long cohort,
        Role role,
        Status status
) {
    public static MemberInfoDto fromEntity(Member member) {
        return new MemberInfoDto(
                member.getMemberId(),
                member.getName(),
                member.getEmail(),
                member.getSchool(),
                member.getCohort(),
                member.getRole(),
                member.getStatus()
        );
    }
}