package BITAmin.BE.member.service;
import BITAmin.BE.member.dto.member.*;
import BITAmin.BE.member.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.util.RedisClient;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.repository.MemberMapper;
import BITAmin.BE.member.repository.MemberRepositoryCustom;
import BITAmin.BE.member.repository.MemberRepository;
import BITAmin.BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final RedisClient redisClient;

    public void updateMember(Long memberId, UpdateMemberRequestDto dto){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        memberMapper.updateFromDto(dto, member);
        System.out.println("member link1 확인: "+member.getLink1());
        memberRepository.save(member);
    }
    public List<MemberInfoDto> getMembersByStatus(List<Status> statusList) {
        List<Member> members;
        if (statusList == null || statusList.isEmpty()) {
            members = memberRepository.findAll();
        } else {
            members = memberRepository.findByStatusIn(statusList);
        }
        return members.stream()
                .map(MemberInfoDto::fromEntity)
                .toList();
    }
    public void deleteMember(Long memberId){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        memberRepository.delete(member);
        redisClient.deleteValue("RefreshToken:"+member.getUsername());
    }
    @Transactional
    public void approveMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        member.setStatusApprove();
    }

    public MemberStatsDto getMemberStats(){
        int total = (int) memberRepository.count();
        int memberTotal = memberRepository.countByRole(Role.MEMBER);
        int adminTotal = memberRepository.countByRole(Role.ADMIN);
        int cohortTotal = memberRepository.countDistinctCohort();
        return new MemberStatsDto(total, memberTotal, adminTotal, cohortTotal);
    }
}
