package smart.warehouse.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import smart.warehouse.dao.custom.WarehouseDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.dao.util.CrudUtil;
import smart.warehouse.entity.Warehouse;

public class WarehouseDAOImpl implements WarehouseDAO{

    @Override
    public Warehouse save(Warehouse entity) throws ConstraintViolationException {
        try {
            System.out.println(entity.getUserId());
            if(CrudUtil.execute("INSERT INTO warehouse VALUES ( ?, ?, ?, ?, ?, ?, ?)",
                    entity.getWarehouseCode(),
                    entity.getUserId(),
                    entity.getWhsId(),
                    entity.getLength(),
                    entity.getWidth(),
                    entity.getReseiveDate(),
                    entity.getDescription()
            )){
                return entity;
            }
            throw new SQLException("Failed to save the Warehouse");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Warehouse update(Warehouse entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("UPDATE Warehouse SET userId=? ,whsId=? ,length=? ,width=? ,receiveDate=? ,description=?  WHERE warehouseCode=?",
                    entity.getUserId(),
                    entity.getWhsId(),
                    entity.getLength(),
                    entity.getWidth(),
                    entity.getReseiveDate(),
                    entity.getDescription(),
                    entity.getWarehouseCode()
            )){
                return entity;
            }
            throw new SQLException("Failed to update the Warehouse");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("DELETE FROM warehouse WHERE warehouseCode=?",pk)) {
                
            } else {
                throw new SQLException("Failed to delete the Warehouse");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Warehouse> findAll() {
        try{
            List<Warehouse> warehouses = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("SELECT * FROM Warehouse");
            while (rst.next()) {                
                warehouses.add(new Warehouse(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDate(6),
                        rst.getString(7)
                ));
            }
            return warehouses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the Warehouse");
        }
    }

    @Override
    public Optional<Warehouse> findByPk(String pk) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM Warehouse WHERE warehouseCode = ?",pk);
            if(rst.next()){
                return Optional.of(new Warehouse(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDate(6),
                        rst.getString(7)
             ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Warehouse details");
        }
    }

    @Override
    public Optional<String> findLastPk() {
        try{
            ResultSet rst = CrudUtil.execute("SELECT warehouseCode FROM Warehouse ORDER BY warehouseCode DESC LIMIT 1");
            if(rst.next()){
                return Optional.of(rst.getString(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Warehouse details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Warehouse WHERE warehouseCode=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = CrudUtil.execute("SELECT COUNT(warehouseCode) AS count FROM Warehouse");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Warehouse> findWarehouseByUserId(String userId) {
        try{
            List<Warehouse> warehouses = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("SELECT * FROM warehouse WHERE userId=?",userId);
            while (rst.next()) {                
                warehouses.add(new Warehouse(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDate(6),
                        rst.getString(7)
                ));
            }
            return warehouses;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the Warehouse");
        }
    }
    
}
