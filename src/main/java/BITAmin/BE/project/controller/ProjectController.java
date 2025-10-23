package BITAmin.BE.project.controller;

import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.dto.ProjectThumbnail;
import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.service.ProjectService;
import BITAmin.BE.project.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final S3Service s3Service;
    private final ProjectService projectService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("projectId") Long projectId
    ) {
        String url = s3Service.uploadFile(file, type);
        projectService.saveUrl(type, url, projectId);
        return ResponseEntity.ok(url);
    }
    @DeleteMapping("/{projectId}/file")
    public ResponseEntity<String> deleteFile(@PathVariable Long projectId, @RequestParam String key) {
        s3Service.deleteFile(key);
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("프로젝트 삭제 완료: " + key);
    }
    @PostMapping("/uploadInfo")
    public ResponseEntity<ApiResponse<ProjectInfoDto>> uploadFileInfo(@RequestBody ProjectInfoDto dto){
        ProjectInfoDto response = projectService.uploadFileInfo(dto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 업로드 성공", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectInfoDto>>> searchProjects(
            @RequestParam(required = false) String cohort,
            @RequestParam(required = false) String period,
            @RequestParam(required = false) Award award
    ) {
        List<Project> projects = projectService.searchProjects(cohort, period, award);
        List<ProjectInfoDto> response = projects.stream()
                .map(ProjectInfoDto::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("프로젝트 검색 완료", response));
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectInfoDto>> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectInfoDto dto) {

        ProjectInfoDto response = projectService.updateProject(projectId, dto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 수정 성공", response));
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProjectThumbnail>>> getAllProjects() {
        List<ProjectThumbnail> projects = projectService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success("프로젝트 전체 조회 성공", projects));
    }
}
