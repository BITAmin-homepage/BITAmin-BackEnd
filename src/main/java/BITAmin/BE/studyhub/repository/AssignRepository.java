package BITAmin.BE.studyhub.repository;

import BITAmin.BE.studyhub.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignRepository extends JpaRepository<Assignment, Long> {
}
