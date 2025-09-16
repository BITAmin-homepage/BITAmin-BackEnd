package BITAmin.BE.studyhub.service;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.generic.GenericService;
import BITAmin.BE.studyhub.dto.AssignInfoDto;
import BITAmin.BE.studyhub.entity.Assignment;
import BITAmin.BE.studyhub.enums.TaskType;
import BITAmin.BE.studyhub.repository.AssignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignService {
    private final GenericService<Assignment, AssignInfoDto> service;
    private final AssignRepository assignRepository;

    public AssignService(AssignRepository assignRepository) {
        this.assignRepository = assignRepository;
        this.service = new GenericService<>(assignRepository);
    }

    public AssignInfoDto uploadAssignment(AssignInfoDto dto) {
        Assignment assignment = dto.toEntity(dto);
        service.save(assignment);
        return dto;
    }

    @Transactional
    public AssignInfoDto updateAssignment(Long assignmentId, AssignInfoDto dto) {
        Assignment assignment = assignRepository.findById(assignmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        service.update(assignmentId, dto);
        return AssignInfoDto.fromEntity(assignment);
    }

    public void deleteAssignment(Long assignmentId) {
        Assignment assignment = assignRepository.findById(assignmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        service.delete(assignmentId);
    }
    @Transactional(readOnly = true)
    public List<Assignment> getAllAssignments() {
        return assignRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Assignment> getAssignmentsByType(TaskType type) {
        return assignRepository.findByType(type);
    }

}
