package smart.warehouse.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import smart.warehouse.dao.custom.ReturnDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.dao.util.CrudUtil;
import smart.warehouse.entity.Return;

public class ReturnDAOImpl implements ReturnDAO{

    @Override
    public Return save(Return entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("INSERT INTO `return` VALUES (?, ?, ?)",
                    entity.getWarehouseCode(),
                    entity.getRerurnedDate(),
                    entity.getPrice()
            )){
                return entity;
            }
            throw new SQLException("Failed to save the Return");
        }catch (SQLException e){
            e.printStackTrace();
            throw new ConstraintViolationException(e);
        }
    }
    
    @Override
    public Return update(Return entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("UPDATE `Return` SET rerurnedDate=?, price=?  WHERE warehouseCode=?",
                    entity.getRerurnedDate(),
                    entity.getPrice(),
                    entity.getWarehouseCode()
            )){
                return entity;
            }
            throw new SQLException("Failed to update the Return");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("DELETE FROM `Return` WHERE warehouseCode=?",pk)) {
            } else {
                throw new SQLException("Failed to delete the Return");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Return> findAll() {
        try{
            List<Return> returns = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("SELECT * FROM `Return`");
            while (rst.next()) {                
                returns.add(new Return(
                        rst.getString(1),
                        rst.getDate(2),
                        rst.getDouble(3)
                ));
            }
            return returns;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the Return");
        }
    }

    @Override
    public Optional<Return> findByPk(String pk) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM `Return` WHERE warehouseCode=?",pk);
            if(rst.next()){
                return Optional.of(new Return(
                        rst.getString(1),
                        rst.getDate(2),
                        rst.getDouble(3)
                        
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Return details");
        }
    }

    @Override
    public Optional<String> findLastPk() {
        try{
            ResultSet rst = CrudUtil.execute("SELECT warehouseCode FROM `Return` ORDER BY warehouseCode DESC LIMIT 1");
            if(rst.next()){
                return Optional.of(rst.getString(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Return details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `Return` WHERE warehouseCode=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = CrudUtil.execute("SELECT COUNT(warehouseCode) AS count FROM `Return`");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
