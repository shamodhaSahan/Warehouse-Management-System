package smart.warehouse.bo.custom.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import smart.warehouse.bo.custom.UserBO;
import smart.warehouse.bo.exception.DuplicateException;
import smart.warehouse.bo.exception.InUseException;
import smart.warehouse.bo.exception.NotFoundException;
import smart.warehouse.bo.util.Convertor;
import smart.warehouse.dao.DaoFactory;
import smart.warehouse.dao.DaoTypes;
import smart.warehouse.dao.custom.ReturnDAO;
import smart.warehouse.dao.custom.UserDAO;
import smart.warehouse.dao.custom.WarehouseDAO;
import smart.warehouse.dao.custom.WarehouseStatusDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.db.DBConnection;
import smart.warehouse.dto.UserDTO;
import smart.warehouse.dto.WarehouseDTO;
import smart.warehouse.entity.User;
import smart.warehouse.entity.Warehouse;
import smart.warehouse.entity.WarehouseStatus;

public class UserBOImpl implements UserBO{
    private final Convertor convertor;
    private final UserDAO userDAO;
    private final WarehouseDAO warehouseDAO;
    private final WarehouseStatusDAO warehouseStatusDAO;
    private final ReturnDAO returnDAO;
    private final Connection connection;

    public UserBOImpl() {
        this.convertor = new Convertor();
        userDAO = DaoFactory.getInstance().getDAO(DaoTypes.USER);
        warehouseDAO = DaoFactory.getInstance().getDAO(DaoTypes.WAREHOUSE); 
        warehouseStatusDAO = DaoFactory.getInstance().getDAO(DaoTypes.WAREHOUSESTATUS);
        returnDAO = DaoFactory.getInstance().getDAO(DaoTypes.RETURN);
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public String getNextId() {
        Optional<String> optional = userDAO.findLastPk();
        if(optional.isPresent()){
            String pk = optional.get().substring(1);
            return String.format("U%03d", Integer.parseInt(pk)+1);
        }
        return "U001";
    }

    @Override
    public Boolean addUser(UserDTO userDTO) throws DuplicateException {
        Optional<User> optional = userDAO.findByNic(userDTO.getNic());
        if(optional.isPresent()){
            throw new DuplicateException("nic dupplicated !");
        }
        User user = convertor.toUser(userDTO);
        return userDAO.save(user) == user;
    }

    @Override
    public UserDTO searchUser(String nic) throws NotFoundException { // returned user with warehouse and status list
        Optional<User> optional = userDAO.findByNic(nic);
        if(optional.isPresent()){
            User user = optional.get();
            List<WarehouseDTO> warehouses = new ArrayList<>();
            for(Warehouse warehouse : warehouseDAO.findWarehouseByUserId(user.getUserId())){
                if(!returnDAO.findByPk(warehouse.getWarehouseCode()).isPresent()){
                    WarehouseStatus status = warehouseStatusDAO.findByPk(warehouse.getWhsId()).get();
                    warehouses.add(convertor.fromWarehouse(warehouse, status));
                }
            }
            return convertor.fromUser(user,warehouses);
        }
        throw new NotFoundException("user not found !");
    }

    @Override
    public List<String> getAllNic() {
        List<String> nics = new ArrayList<>();
        for(User user : userDAO.findAll()){
            nics.add(user.getNic());
        }
        return nics;
    }

    @Override
    public Boolean deleteUser(String nic) throws NotFoundException, InUseException  {
        Optional<User> findByNic = userDAO.findByNic(nic);
        if(!findByNic.isPresent())
            throw new NotFoundException("user not found !");
        List<Warehouse> warehouses = warehouseDAO.findWarehouseByUserId(findByNic.get().getUserId());
        for(Warehouse w : warehouses){
            if(!returnDAO.findByPk(w.getWarehouseCode()).isPresent()){
                throw new InUseException("user have non returned storage");
            }
        }
        try{
            userDAO.deleteByPk(findByNic.get().getUserId());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
