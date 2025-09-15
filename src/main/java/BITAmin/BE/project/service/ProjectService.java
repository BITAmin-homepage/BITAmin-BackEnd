package BITAmin.BE.project.service;

import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.enums.Period;
import BITAmin.BE.project.repository.ProjectRepository;
import BITAmin.BE.project.repository.ProjectSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectInfoDto uploadFileInfo(ProjectInfoDto dto){
        Project projectInfo = dto.toEntity(dto);
        projectRepository.save(projectInfo);
        return dto;
    }
    public List<Project> searchProjects(String cohort, Period period, Award award) {
        Specification<Project> spec = Specification
                .where(ProjectSpecification.hasCohort(cohort))
                .and(ProjectSpecification.hasPeriod(period))
                .and(ProjectSpecification.hasAward(award));

        return projectRepository.findAll(spec);
    }
    @Transactional
    public ProjectInfoDto updateProject(Long projectId, ProjectInfoDto dto){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
        project.update(dto);
        return ProjectInfoDto.fromEntity(project);
    }
}
