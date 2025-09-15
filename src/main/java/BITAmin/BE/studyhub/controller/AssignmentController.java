package BITAmin.BE.studyhub.controller;

import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.studyhub.dto.AssignInfoDto;
import BITAmin.BE.studyhub.service.AssignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignService assignService;

    @PostMapping
    public ResponseEntity<ApiResponse<AssignInfoDto>> uploadAssignment(@RequestBody AssignInfoDto dto) {
        AssignInfoDto response = assignService.uploadAssignment(dto);
        return ResponseEntity.ok(ApiResponse.success("과제 업로드 성공", response));
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<ApiResponse<AssignInfoDto>> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AssignInfoDto dto) {

        AssignInfoDto response = assignService.updateAssignment(assignmentId, dto);
        return ResponseEntity.ok(ApiResponse.success("과제 수정 성공", response));
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId) {
        assignService.deleteAssignment(assignmentId);
        return ResponseEntity.ok("과제 삭제 완료: " + assignmentId);
    }
}