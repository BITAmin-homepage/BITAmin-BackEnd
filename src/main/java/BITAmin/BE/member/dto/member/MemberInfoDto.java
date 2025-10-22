package BITAmin.BE.member.dto.member;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.enums.Status;


public record MemberInfoDto(
        Long memberId,
        String name,
        String gender,
        String birthDate,
        String phone,
        String email,
        String school,
        Long cohort,
        Role role,
        Status status,
        String link1,
        String link2
) {
    public static MemberInfoDto fromEntity(Member member) {
        return new MemberInfoDto(
                member.getMemberId(),
                member.getName(),
                member.getGender(),
                member.getBirthDate(),
                member.getPhone(),
                member.getEmail(),
                member.getSchool(),
                member.getCohort(),
                member.getRole(),
                member.getStatus(),
                member.getLink1(),
                member.getLink2()
        );
    }
}