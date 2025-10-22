package BITAmin.BE.member.service;
import BITAmin.BE.member.dto.member.*;
import BITAmin.BE.member.enums.Status;
import BITAmin.BE.member.repository.AgedMemberRepository;
import jakarta.transaction.Transactional;
import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.util.RedisClient;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.repository.MemberRepository;
import BITAmin.BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AgedMemberRepository agedMemberRepository;
    private final RedisClient redisClient;

    public MemberInfoDto getMemberInfo(Long memberId){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return MemberInfoDto.fromEntity(member);
    }
    public List<MemberIntro> getMemberIntroduce() {
        // 일반 Member에서
        List<MemberIntro> normalMembers = memberRepository.findAll().stream()
                .map(member -> new MemberIntro(
                        member.getCohort(),
                        member.getName(),
                        member.getLink1(),
                        member.getLink2(),
                        member.getDepart()
                ))
                .collect(Collectors.toList());

        // AgedMember에서
        List<MemberIntro> agedMembers = agedMemberRepository.findAll().stream()
                .map(aged -> new MemberIntro(
                        aged.getCohort(),
                        aged.getName(),
                        aged.getLink1(),
                        aged.getLink2(),
                        aged.getDepart()
                ))
                .collect(Collectors.toList());

        // 두 리스트 합치기
        List<MemberIntro> allMembers = new ArrayList<>();
        allMembers.addAll(normalMembers);
        allMembers.addAll(agedMembers);

        return allMembers;
    }
    public void updateMember(Long memberId, UpdateMemberRequestDto dto){
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        //MemberMapper 오류로 인한 수동 setter
        member.setName(dto.name());
        member.setGender(dto.gender());
        member.setBirthDate(dto.birthDate());
        member.setSchool(dto.school());
        member.setPhone(dto.phone());
        member.setEmail(dto.email());
        member.setCohort(dto.cohort());
        member.setRole(dto.role());
        member.setLink1(dto.link1());
        member.setLink2(dto.link2());
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
