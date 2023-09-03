package smart.warehouse.dao.custom;

import java.util.List;
import smart.warehouse.dao.CrudDAO;
import smart.warehouse.entity.Warehouse;

public interface WarehouseDAO  extends CrudDAO<Warehouse, String>{

    public List<Warehouse> findWarehouseByUserId(String userId);
    
}
