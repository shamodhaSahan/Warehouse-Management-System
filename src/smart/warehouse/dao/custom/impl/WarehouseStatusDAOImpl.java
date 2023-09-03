package smart.warehouse.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import smart.warehouse.dao.custom.WarehouseStatusDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.dao.util.CrudUtil;
import smart.warehouse.entity.WarehouseStatus;

public class WarehouseStatusDAOImpl implements WarehouseStatusDAO{

    @Override
    public WarehouseStatus save(WarehouseStatus entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("INSERT INTO warehousestatus VALUES (?, ?, ?, ?)",
                    entity.getWhsId(),
                    entity.getFullLength(),
                    entity.getFullWidth(),
                    entity.getFullPrice()
            )){
                return entity;
            }
            throw new SQLException("Failed to save the Warehouse Status");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }
    
    @Override
    public WarehouseStatus update(WarehouseStatus entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("UPDATE WarehouseStatus SET fullLength=? ,fullWidth=? ,fullPrice=? WHERE WhsId=?",
                    entity.getFullLength(),
                    entity.getFullWidth(),
                    entity.getFullPrice(),
                    entity.getWhsId()
            )){
                return entity;
            }
            throw new SQLException("Failed to update the Warehouse Status");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("DELETE FROM warehousestatus WHERE whsId = ?",pk)) {
            } else {
                throw new SQLException("Failed to delete the Warehouse Status");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<WarehouseStatus> findAll() {
        try{
            List<WarehouseStatus> warehouseStatuses = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("SELECT * FROM WarehouseStatus");
            while (rst.next()) {                
                warehouseStatuses.add(new WarehouseStatus(
                        rst.getString(1),
                        rst.getDouble(2),
                        rst.getDouble(3),
                        rst.getDouble(4)
                ));
            }
            return warehouseStatuses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the Warehouse Status");
        }
    }

    @Override
    public Optional<WarehouseStatus> findByPk(String pk) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM WarehouseStatus WHERE WhsId=?",pk);
            if(rst.next()){
                return Optional.of(new WarehouseStatus(
                        rst.getString(1),
                        rst.getDouble(2),
                        rst.getDouble(3),
                        rst.getDouble(4)
             ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Warehouse Status");
        }
    }

    @Override
    public Optional<String> findLastPk() {
        try{
            ResultSet rst = CrudUtil.execute("SELECT WhsId FROM WarehouseStatus ORDER BY WhsId DESC LIMIT 1");
            if(rst.next()){
                return Optional.of(rst.getString(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Warehouse Status");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM WarehouseStatus WHERE WhsId=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = CrudUtil.execute("SELECT COUNT(WhsId) AS count FROM WarehouseStatus");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
