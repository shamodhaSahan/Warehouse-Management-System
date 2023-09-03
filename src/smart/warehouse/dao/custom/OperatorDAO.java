package smart.warehouse.dao.custom;

import java.util.Optional;
import smart.warehouse.dao.CrudDAO;
import smart.warehouse.entity.Operator;

public interface OperatorDAO  extends CrudDAO<Operator, String>{

    public Optional<Operator> findByNic(String nic);
    
}
