package BITAmin.BE.global.generic;

public interface CrudEntity<D> {
    D toDto();
    void update(D dto);
}