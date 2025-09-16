package BITAmin.BE.studyhub.repository;

import BITAmin.BE.studyhub.entity.Assignment;
import BITAmin.BE.studyhub.enums.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByType(TaskType type);
}
