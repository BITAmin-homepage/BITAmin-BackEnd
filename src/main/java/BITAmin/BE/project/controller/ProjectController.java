package BITAmin.BE.project.controller;

import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.project.dto.ProjectDetail;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.dto.ProjectPpt;
import BITAmin.BE.project.dto.ProjectThumbnail;
import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.repository.ProjectRepository;
import BITAmin.BE.project.service.LibreOfficeService;
import BITAmin.BE.project.service.ProjectService;
import BITAmin.BE.project.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final S3Service s3Service;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final LibreOfficeService libreOfficeService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("projectId") Long projectId
    ) {
        try {
            long sizeMB = file.getSize() / (1024 * 1024);
            if (sizeMB <= 10) {
                String pptxUrl = s3Service.uploadFile(file, type);
                return ResponseEntity.ok(pptxUrl);
            }
            File pdfFile = libreOfficeService.convertToPdf(file);
            System.out.println("pdfFile 이름: "+pdfFile.getName());
            String pdfUrl = s3Service.uploadPdf(pdfFile);
            libreOfficeService.cleanTempFiles(pdfFile);
            return ResponseEntity.ok(pdfUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("업로드 실패: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProject(
            @RequestParam String projectTitle) {
        Long projectId = projectRepository.findIdByTitle(projectTitle)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        String pptPrefix = "ppt/" + projectTitle + "/";
        String thumbPrefix = "thumbnail/" + projectTitle + "/";
        s3Service.deleteFolder(pptPrefix);
        s3Service.deleteFolder(thumbPrefix);
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("프로젝트 및 연관 파일 삭제 완료");
    }

    @PostMapping("/uploadInfo")
    public ResponseEntity<ApiResponse<ProjectInfoDto>> uploadFileInfo(@RequestBody ProjectInfoDto dto){
        ProjectInfoDto response = projectService.uploadFileInfo(dto);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 업로드 성공", response));
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
    @GetMapping
    public ResponseEntity<ApiResponse<ProjectDetail>> getCertainProjects(
            @RequestParam Long projectId
    ){
        ProjectDetail project=projectService.getCertainProject(projectId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 조회 성공", project));
    }
    @GetMapping("/ppt/{projectId}")
    public ResponseEntity<ApiResponse<ProjectPpt>> getProjectPpt(
            @PathVariable Long projectId
    ){
        ProjectPpt ppt = projectService.getProjectPpt(projectId);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 ppt 반환성공", ppt));

    }
}
