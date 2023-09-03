package smart.warehouse.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.entity.SuperEntity;

public interface CrudDAO <T extends SuperEntity ,ID extends Serializable> extends SuperDAO{
    T save(T entity) throws ConstraintViolationException;

    T update(T entity) throws ConstraintViolationException;

    void deleteByPk(ID pk) throws ConstraintViolationException;

    List<T> findAll();

    Optional<T> findByPk(ID pk);
    
    Optional<String> findLastPk();

    boolean existByPk(ID pk);

    long count();
}
