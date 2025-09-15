package BITAmin.BE.global.generic;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class GenericService<E extends CrudEntity<D>, D> {

    private final JpaRepository<E, Long> repository;
    /**CREATE**/
    @Transactional
    public D save(E entity) {
        E saved = repository.save(entity);
        return saved.toDto();
    }
    /** READ */
    @Transactional(readOnly = true)
    public D findById(Long id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        return entity.toDto();
    }

    /** UPDATE */
    @Transactional
    public D update(Long id, D dto) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        entity.update(dto);
        return entity.toDto();
    }

    /** DELETE */
    @Transactional
    public void delete(Long id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_NOT_FOUND));
        repository.delete(entity);
    }
}
