package smart.warehouse.bo.custom.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import smart.warehouse.bo.custom.StorageBO;
import smart.warehouse.bo.exception.NotFoundException;
import smart.warehouse.bo.util.Convertor;
import smart.warehouse.dao.DaoFactory;
import smart.warehouse.dao.DaoTypes;
import smart.warehouse.dao.custom.ReturnDAO;
import smart.warehouse.dao.custom.WarehouseDAO;
import smart.warehouse.dao.custom.WarehouseStatusDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.db.DBConnection;
import smart.warehouse.dto.ReturnDTO;
import smart.warehouse.dto.WarehouseDTO;
import smart.warehouse.dto.WarehouseStatusDTO;
import smart.warehouse.entity.Warehouse;
import smart.warehouse.entity.WarehouseStatus;

public class StorageBOImpl implements StorageBO{
    private final Convertor convertor;
    private final Connection connection;
    private final WarehouseDAO warehouseDAO;
    private final WarehouseStatusDAO warehouseStatusDAO;
    private final ReturnDAO returnDAO;

    public StorageBOImpl() {
        this.convertor = new Convertor();
        this.connection = DBConnection.getInstance().getConnection();
        this.warehouseDAO = DaoFactory.getInstance().getDAO(DaoTypes.WAREHOUSE);
        this.warehouseStatusDAO = DaoFactory.getInstance().getDAO(DaoTypes.WAREHOUSESTATUS);
        this.returnDAO = DaoFactory.getInstance().getDAO(DaoTypes.RETURN);
    }
    
    @Override
    public String getNextWareHouseId() {
        Optional<String> optional = warehouseDAO.findLastPk();
        if(optional.isPresent()){
            String pk = optional.get().substring(1);
            return String.format("W%03d", Integer.parseInt(pk)+1);
        }
        return "W001";
    }
    
    private String getNextWHSId() {
        Optional<String> optional = warehouseStatusDAO.findLastPk();
        if(optional.isPresent()){
            String pk = optional.get().substring(1);
            return String.format("S%03d", Integer.parseInt(pk)+1);
        }
        return "S001";
    }

    @Override
    public WarehouseStatusDTO getCurrentStatus() {
        Optional<String> optional = warehouseStatusDAO.findLastPk();
        if(optional.isPresent()){
            Optional<WarehouseStatus> findByPk = warehouseStatusDAO.findByPk(optional.get());
            if(findByPk.isPresent()){
                return convertor.fromWarehouseStatus(findByPk.get());
            }
        }
        return null;
    }

    @Override
    public Boolean addWarehouse(WarehouseDTO warehouseDTO) {
        try {
            connection.setAutoCommit(false);
            
            WarehouseStatusDTO warehouseStatusDTO = warehouseDTO.getWarehouseStatusDTO();
            warehouseStatusDTO.setWhsId(getNextWHSId());
            warehouseStatusDAO.save(convertor.toWarehouseStatus(warehouseStatusDTO));
            warehouseDTO.setWarehouseStatusDTO(warehouseStatusDTO);
            warehouseDAO.save(convertor.toWarehouse(warehouseDTO));
            
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public WarehouseDTO searchWarehouse(String warehouseCode) throws NotFoundException {
        Optional<Warehouse> optionalWarehouse = warehouseDAO.findByPk(warehouseCode);
        if(optionalWarehouse.isPresent()){
            Warehouse warehouse = optionalWarehouse.get();
            Optional<WarehouseStatus> optionalWHS = warehouseStatusDAO.findByPk(warehouse.getWhsId());
            return convertor.fromWarehouse(warehouse,optionalWHS.get());
        }
        throw new NotFoundException("warehouse not found !");
    }

    @Override
    public Boolean returnWarehouse(List<ReturnDTO> returnDTO) {
        try{
            connection.setAutoCommit(false);
            for(ReturnDTO dTO : returnDTO){
                returnDAO.save(convertor.toReturn(dTO));
            }
            connection.commit();
            return true;
        }catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Boolean deleteStorage(WarehouseDTO warehouseDTO) {
        try {
            connection.setAutoCommit(false);
            returnDAO.deleteByPk(warehouseDTO.getWarehouseCode());
            warehouseDAO.deleteByPk(warehouseDTO.getWarehouseCode());
            connection.commit();
            return true;
        } catch (ConstraintViolationException e) {
            return false;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(StorageBOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<String> getAllReturnedWarehouseID() {
        List<String> strings = new ArrayList<>();
        for(Warehouse warehouse : warehouseDAO.findAll()){
            if(returnDAO.findByPk(warehouse.getWarehouseCode()).isPresent()){
                strings.add(warehouse.getWarehouseCode());
            }
        }
        return strings;
    }

    @Override
    public List<String> getAllNonReturnedWarehouseID() {
        List<String> strings = new ArrayList<>();
        for(Warehouse warehouse : warehouseDAO.findAll()){
            if(!returnDAO.findByPk(warehouse.getWarehouseCode()).isPresent()){
                strings.add(warehouse.getWarehouseCode());
            }
        }
        return strings;
    }

    @Override
    public Boolean isStatusEmpty() {
        return warehouseStatusDAO.count() > 0;
    }

    @Override
    public Boolean saveStatus(WarehouseStatusDTO warehouseStatusDTO) {
        WarehouseStatus status = convertor.toWarehouseStatus(warehouseStatusDTO);
        return warehouseStatusDAO.save(status) == status;
    }
}
