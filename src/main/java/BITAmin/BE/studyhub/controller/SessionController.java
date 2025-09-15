package BITAmin.BE.studyhub.controller;

import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.studyhub.dto.SessionInfoDto;
import BITAmin.BE.studyhub.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;
    @PostMapping()
    public ResponseEntity<ApiResponse<SessionInfoDto>> uploadSession(@RequestBody SessionInfoDto dto){
        SessionInfoDto response = sessionService.uploadSession(dto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 업로드 성공", response));
    }
    @PutMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<SessionInfoDto>> updateSession(
            @PathVariable Long sessionId,
            @RequestBody SessionInfoDto dto) {

        SessionInfoDto response = sessionService.updateSession(sessionId, dto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 수정 성공", response));
    }
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable Long sessionId){
        sessionService.deleteSession(sessionId);
        return ResponseEntity.ok("프로젝트 삭제 완료: " + sessionId);
    }

}
