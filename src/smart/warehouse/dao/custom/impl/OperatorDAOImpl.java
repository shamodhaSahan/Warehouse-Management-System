package smart.warehouse.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import smart.warehouse.dao.custom.OperatorDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.dao.util.CrudUtil;
import smart.warehouse.entity.Operator;


public class OperatorDAOImpl implements OperatorDAO{

    @Override
    public Operator save(Operator entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("INSERT INTO operator VALUES (?, ?, ?, ?, ?, ?)",
                    entity.getOperatorId(),
                    entity.getOperatorName(),
                    entity.getPassword(),
                    entity.getNic(),
                    entity.getAddress(),
                    entity.getEmail()
            )){
                return entity;
            }
            throw new SQLException("Failed to save the Operator");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Operator update(Operator entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("UPDATE Operator SET operatorName=? ,password=? ,nic=? ,address=? ,email=?  WHERE operatorId=?",
                    entity.getOperatorName(),
                    entity.getPassword(),
                    entity.getNic(),
                    entity.getAddress(),
                    entity.getEmail(),
                    entity.getOperatorId()
            )){
                return entity;
            }
            throw new SQLException("Failed to update the Operator");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("DELETE FROM Operator WHERE operatorId=?",pk)) {
            } else {
                throw new SQLException("Failed to delete the Operator");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Operator> findAll() {
        try{
            List<Operator> operators = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("SELECT * FROM Operator");
            while (rst.next()) {                
                operators.add(new Operator(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                ));
            }
            return operators;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the Operator");
        }
    }

    @Override
    public Optional<Operator> findByPk(String pk) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM Operator WHERE operatorId=?",pk);
            if(rst.next()){
                return Optional.of(new Operator(
                     rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
             ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Operator details");
        }
    }

    @Override
    public Optional<String> findLastPk() {
        try{
            ResultSet rst = CrudUtil.execute("SELECT operatorId FROM Operator ORDER BY operatorId DESC LIMIT 1");
            if(rst.next()){
                return Optional.of(rst.getString(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Operator details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Operator WHERE operatorId=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = CrudUtil.execute("SELECT COUNT(operatorId) AS count FROM Operator");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Operator> findByNic(String nic) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM Operator WHERE nic=?",nic);
            if(rst.next()){
                return Optional.of(new Operator(
                     rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
             ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Operator details");
        }
    }
    
}
