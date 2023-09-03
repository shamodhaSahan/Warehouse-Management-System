package smart.warehouse.bo.custom;

import smart.warehouse.bo.SuperBO;
import smart.warehouse.bo.exception.DuplicateException;
import smart.warehouse.bo.exception.NotFoundException;
import smart.warehouse.dto.OperatorDTO;

public interface OperatorBO extends SuperBO{
    public String getNextOperatorId();
    public Boolean addOperator(OperatorDTO operatorDTO) throws DuplicateException;
    public void setIsManager(boolean isManager);
    public Boolean getIsManager();
    public OperatorDTO getOperator(String operatorId) throws NotFoundException;
}
