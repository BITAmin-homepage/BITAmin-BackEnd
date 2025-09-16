package BITAmin.BE.studyhub.repository;

import BITAmin.BE.studyhub.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
