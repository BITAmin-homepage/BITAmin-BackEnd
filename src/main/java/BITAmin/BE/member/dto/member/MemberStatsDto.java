package BITAmin.BE.member.dto.member;

public record MemberStatsDto(
        int total,
        int memberTotal,
        int adminTotal,
        int cohortTotal
)
{}
