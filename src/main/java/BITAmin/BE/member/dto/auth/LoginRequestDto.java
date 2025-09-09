package BITAmin.BE.member.dto.auth;

import BITAmin.BE.member.entity.Member;

public record LoginRequestDto(
        String username,
        String password
) {

}
