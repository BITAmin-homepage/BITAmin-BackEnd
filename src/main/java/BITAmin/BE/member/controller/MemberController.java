package BITAmin.BE.member.controller;

import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.member.dto.auth.SignupReqeustDto;
import BITAmin.BE.member.dto.member.*;
import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Status;
import BITAmin.BE.member.service.AuthService;
import BITAmin.BE.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberInfoDto>> memberInfo(@PathVariable Long memberId){
        MemberInfoDto dto = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(ApiResponse.success("회원 정보 조회 성공", dto));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> addMember(@RequestBody SignupReqeustDto dto) {
        authService.signup(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("멤버 추가 완료", null));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<MemberInfoDto>>> getMembersByStatus(
            @RequestParam(required = false) List<Status> status
    ) {
        List<MemberInfoDto> members = memberService.getMembersByStatus(status);
        ApiResponse<List<MemberInfoDto>> response = ApiResponse.success("회원 상태별 조회 완료", members);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/status/{memberId}")
    public ResponseEntity<ApiResponse<String>> memberStatus(@PathVariable Long memberId){
        memberService.approveMember(memberId);
        return ResponseEntity
                .ok(ApiResponse.success("멤버 승인 완료", null));
    }
    @PutMapping("/update/{memberId}")
    public ResponseEntity<ApiResponse<String>> updateMember(@PathVariable Long memberId, @RequestBody UpdateMemberRequestDto dto) {
        memberService.updateMember(memberId, dto);
        return ResponseEntity
                .ok(ApiResponse.success("수정 완료", null));
    }

    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity
                .ok(ApiResponse.success("삭제 완료", null));
    }
    // 통계 확인
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<MemberStatsDto>> getMemberStats() {
        MemberStatsDto stats = memberService.getMemberStats();
        return ResponseEntity
                .ok(ApiResponse.success("멤버 통계 조회 성공", stats));
    }
}