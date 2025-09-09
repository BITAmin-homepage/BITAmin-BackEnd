package BITAmin.BE.member.dto.auth;
import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import lombok.Getter;


public record SignupReqeustDto(
        String name,
        String gender,
        String birthDate,
        String school,
        String phone,
        String email,
        Long cohort,
        Role role,
        String username,
        String password
) {
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .name(this.name)
                .gender(this.gender)
                .birthDate(this.birthDate)
                .school(this.school)
                .phone(this.phone)
                .email(this.email)
                .cohort(this.cohort)
                .role(this.role)
                .username(this.username)
                .password(encodedPassword)
                .build();
    }
}