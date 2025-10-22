package BITAmin.BE.member.dto.member;

import BITAmin.BE.member.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record UpdateMemberRequestDto(
        String name,
        String gender,
        String birthDate,
        String school,
        String phone,
        String email,
        Long cohort,
        Role role,
        String link1,
        String link2
) {
}
