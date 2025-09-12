package BITAmin.BE.project.controller;

import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.service.ProjectService;
import BITAmin.BE.project.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final S3Service s3Service;
    private final ProjectService projectService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) {
        String url = s3Service.uploadFile(file, type);
        return ResponseEntity.ok(url);
    }
    @PostMapping("/uploadInfo")
    public ResponseEntity<ApiResponse<ProjectInfoDto>> uploadFileInfo(@RequestBody ProjectInfoDto dto){
        ProjectInfoDto response = projectService.uploadFileInfo(dto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 업로드 성공", response));
    }
}
