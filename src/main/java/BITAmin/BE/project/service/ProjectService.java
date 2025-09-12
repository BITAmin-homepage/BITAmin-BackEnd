package BITAmin.BE.project.service;

import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectInfoDto uploadFileInfo(ProjectInfoDto dto){
        Project projectInfo = dto.toEntity(dto);
        projectRepository.save(projectInfo);
        return dto;
    }
}
