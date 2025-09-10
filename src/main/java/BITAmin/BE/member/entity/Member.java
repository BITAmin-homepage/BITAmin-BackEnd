package BITAmin.BE.member.entity;

import BITAmin.BE.member.enums.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.persistence.Entity;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String name;
    private String gender;
    private String birthDate;
    private String school;
    private String phone;
    private String email;
    private Long cohort;
    private Role role;
    private String username;
    private String password;
}
