package smart.warehouse.bo.custom;

import java.util.List;
import smart.warehouse.bo.SuperBO;
import smart.warehouse.bo.exception.DuplicateException;
import smart.warehouse.bo.exception.InUseException;
import smart.warehouse.bo.exception.NotFoundException;
import smart.warehouse.dto.UserDTO;

public interface UserBO extends SuperBO{
    public String getNextId();
    public Boolean addUser(UserDTO userDTO) throws DuplicateException;
    public UserDTO searchUser(String nic) throws NotFoundException;
    public List<String> getAllNic();
    public Boolean deleteUser(String nic) throws NotFoundException, InUseException;
}
