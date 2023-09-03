package smart.warehouse.dao.custom.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import smart.warehouse.dao.custom.UserDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.dao.util.CrudUtil;
import smart.warehouse.entity.User;

public class UserDAOImpl implements UserDAO{

    @Override
    public User save(User entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?)",
                    entity.getUserId(),
                    entity.getName(),
                    entity.getAddress(),
                    entity.getNic(),
                    entity.getTelNumber(),
                    entity.getEmail()
            )){
                return entity;
            }
            throw new SQLException("Failed to save the User");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }
    
    @Override
    public User update(User entity) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("UPDATE User SET name=? ,address=? ,nic=? ,telNumber=? ,email=?  WHERE userId=?",
                    entity.getName(),
                    entity.getAddress(),
                    entity.getNic(),
                    entity.getTelNumber(),
                    entity.getEmail(),
                    entity.getUserId()
            )){
                return entity;
            }
            throw new SQLException("Failed to update the User");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(CrudUtil.execute("DELETE FROM User WHERE userId=?",pk)) {
            } else {
                throw new SQLException("Failed to delete the User");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try{
            List<User> users = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("SELECT * FROM User");
            while (rst.next()) {                
                users.add(new User(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the User");
        }
    }

    @Override
    public Optional<User> findByPk(String pk) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM User WHERE userId=?",pk);
            if(rst.next()){
                return Optional.of(new User(
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
            throw new RuntimeException("Failed to find the Operator User");
        }
    }

    @Override
    public Optional<String> findLastPk() {
        try{
            ResultSet rst = CrudUtil.execute("SELECT userId FROM User ORDER BY userId DESC LIMIT 1");
            if(rst.next()){
                return Optional.of(rst.getString(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the User");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM User WHERE userId=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = CrudUtil.execute("SELECT COUNT(userId) AS count FROM User");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByNic(String nic) {
        try{
            ResultSet rst = CrudUtil.execute("SELECT * FROM User WHERE nic=?",nic);
            if(rst.next()){
                return Optional.of(new User(
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
            throw new RuntimeException("Failed to find the Operator User");
        }
    }
    
}
