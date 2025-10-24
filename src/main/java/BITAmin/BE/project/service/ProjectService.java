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
import BITAmin.BE.project.repository.ProjectSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
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
    public List<Project> searchProjects(String cohort, String period, Award award) {
        Specification<Project> spec = Specification
                .where(ProjectSpecification.hasCohort(cohort))
                .and(ProjectSpecification.hasPeriod(period))
                .and(ProjectSpecification.hasAward(award));

        return projectRepository.findAll(spec);
    }
    public void deleteProject(Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
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
        String[] typeParts = type.split("/");
        String folderType = typeParts[0];

        if ("thumbnail".equals(folderType)) {
            project.setThumbnail(url);
        } else if ("ppt".equals(folderType)) {
            project.setPpt(url);
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
