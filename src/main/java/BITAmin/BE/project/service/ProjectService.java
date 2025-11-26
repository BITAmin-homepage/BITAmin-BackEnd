package BITAmin.BE.project.service;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.generic.GenericService;
import BITAmin.BE.project.dto.ProjectDetail;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.dto.ProjectPpt;
import BITAmin.BE.project.dto.ProjectThumbnail;
import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final GenericService<Project, ProjectInfoDto> service;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.service = new GenericService<>(projectRepository);
    }

    public ProjectInfoDto uploadFileInfo(ProjectInfoDto dto){
        Project projectInfo = dto.toEntity(dto);
        ProjectInfoDto savedDto = service.save(projectInfo);
        return savedDto;
    }
    public void deleteProject(Long projectId){
        service.delete(projectId);
    }
    @Transactional
    public ProjectInfoDto updateProject(Long projectId, ProjectInfoDto dto){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        service.update(projectId, dto);
        return ProjectInfoDto.fromEntity(project);
    }
    public ProjectDetail getCertainProject(Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        return ProjectDetail.fromEntity(project);
    }
    public ProjectPpt getProjectPpt(Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        return new ProjectPpt(project.getPpt());
    }
    public void saveUrl(String type, String url, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        System.out.println("[saveUrl] type = " + type);
        // MIME 타입도 처리
        if (type.startsWith("thumbnail")) {
            project.setThumbnail(url);
        } else if (type.startsWith("ppt") ||
                type.equals("application/vnd.ms-powerpoint") ||
                type.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
            project.setPpt(url);
        } else if (type.startsWith("pdf") ||
                type.equals("application/pdf")) {
            project.setPpt(url);
        } else {
            throw new IllegalArgumentException("지원하지 않는 type: " + type);
        }

        projectRepository.save(project);
    }
    public List<ProjectThumbnail> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        // Entity → DTO 변환
        return projects.stream()
                .map(project -> new ProjectThumbnail(
                        project.getProjectId(),
                        project.getThumbnail(),
                        project.getPpt(),
                        project.getTitle(),
                        project.getCohort() == null
                                ? List.of()
                                : Arrays.stream(project.getCohort().split(","))
                                .map(String::trim)
                                .toList(),
                        project.getCategory(),
                        project.getPeriod(),
                        project.getMember(),
                        project.getAward()
                ))
                .toList();
    }
}
