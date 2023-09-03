package smart.warehouse.dao.custom;

import java.util.Optional;
import smart.warehouse.dao.CrudDAO;
import smart.warehouse.entity.User;

public interface UserDAO extends CrudDAO<User, String>{
    public Optional<User> findByNic(String nic);
}
