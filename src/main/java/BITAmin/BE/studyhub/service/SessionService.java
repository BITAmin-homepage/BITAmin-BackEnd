package BITAmin.BE.studyhub.service;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.generic.GenericService;
import BITAmin.BE.project.dto.ProjectInfoDto;
import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.repository.ProjectRepository;
import BITAmin.BE.studyhub.dto.SessionInfoDto;
import BITAmin.BE.studyhub.entity.Session;
import BITAmin.BE.studyhub.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    private final GenericService<Session, SessionInfoDto> service;
    private final SessionRepository sessionRepository;
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        this.service = new GenericService<>(sessionRepository);
    }
    public SessionInfoDto uploadSession(SessionInfoDto dto){
        Session session = dto.toEntity(dto);
        service.save(session);
        return dto;
    }
    @Transactional
    public SessionInfoDto updateSession(Long sessionId, SessionInfoDto dto){
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        service.update(sessionId, dto);
        return SessionInfoDto.fromEntity(session);
    }
    public void deleteSession(Long sessionId){
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        service.delete(sessionId);
    }

    public List<SessionInfoDto> getAllSessions() {
        return sessionRepository.findAll()
                .stream().map(Session::toDto)
                .toList();
    }

    public List<SessionInfoDto> searchSessions(String keyword) {
        return sessionRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream().map(Session::toDto)
                .toList();
    }
}
