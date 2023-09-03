package smart.warehouse.bo.custom.impl;

import java.util.Optional;
import smart.warehouse.bo.custom.OperatorBO;
import smart.warehouse.bo.exception.DuplicateException;
import smart.warehouse.bo.exception.NotFoundException;
import smart.warehouse.bo.util.Convertor;
import smart.warehouse.dao.DaoFactory;
import smart.warehouse.dao.DaoTypes;
import smart.warehouse.dao.SuperDAO;
import smart.warehouse.dao.custom.OperatorDAO;
import smart.warehouse.dao.exception.ConstraintViolationException;
import smart.warehouse.dto.OperatorDTO;
import smart.warehouse.entity.Operator;

public class OperatorBOImpl implements OperatorBO{
    private final Convertor convertor;
    private static boolean isManager;
    private final OperatorDAO operatorDAO ;

    public OperatorBOImpl() {
        this.convertor = new Convertor();
        this.operatorDAO = DaoFactory.getInstance().getDAO(DaoTypes.OPERATOR);
    }
    

    @Override
    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    @Override
    public Boolean getIsManager() {
        return this.isManager;
    }

    @Override
    public OperatorDTO getOperator(String operatorId) throws NotFoundException {
        Optional<Operator> optional = operatorDAO.findByPk(operatorId);
        if(optional.isPresent()){
            return convertor.fromOperators(optional.get());
        }
        throw new NotFoundException("operator not found !");
    }

    @Override
    public String getNextOperatorId() {
        Optional<String> optional = operatorDAO.findLastPk();
        if(optional.isPresent()){
            String pk = optional.get().substring(1);
            return String.format("O%03d", Integer.parseInt(pk)+1);
        }
        return "O001";
    }

    @Override
    public Boolean addOperator(OperatorDTO operatorDTO) throws DuplicateException {
        Optional<Operator> findByNic = operatorDAO.findByNic(operatorDTO.getNic());
        if(findByNic.isPresent()){
            throw new DuplicateException("nic is dupplicate !");
        }
        try {
            operatorDAO.save(convertor.toOperator(operatorDTO));
            return true;
        } catch (ConstraintViolationException e) {
            return false;
        }
    }
    
}
