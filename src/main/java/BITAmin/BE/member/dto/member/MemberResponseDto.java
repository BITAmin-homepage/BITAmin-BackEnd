package BITAmin.BE.member.dto.member;

import BITAmin.BE.member.entity.Member;

public record MemberResponseDto(
   Member member,
   Long number,
   int page
) {}
