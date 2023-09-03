package smart.warehouse.bo.util;

import java.util.List;
import smart.warehouse.dto.OperatorDTO;
import smart.warehouse.dto.ReturnDTO;
import smart.warehouse.dto.UserDTO;
import smart.warehouse.dto.WarehouseDTO;
import smart.warehouse.dto.WarehouseStatusDTO;
import smart.warehouse.entity.Operator;
import smart.warehouse.entity.Return;
import smart.warehouse.entity.User;
import smart.warehouse.entity.Warehouse;
import smart.warehouse.entity.WarehouseStatus;

public class Convertor {

    public WarehouseStatusDTO fromWarehouseStatus(WarehouseStatus entity) {
        return new WarehouseStatusDTO(entity.getWhsId(), entity.getFullLength(), entity.getFullWidth(), entity.getFullPrice());
    }

    public OperatorDTO fromOperators(Operator operator) {
        return new OperatorDTO(operator.getOperatorId(), operator.getOperatorName(), operator.getPassword(), operator.getNic(), operator.getAddress(), operator.getEmail());
    }

    public Operator toOperator(OperatorDTO operatorDTO) {
        return new Operator(operatorDTO.getOperatorId(), operatorDTO.getOperatorName(), operatorDTO.getPassword(), operatorDTO.getNic(), operatorDTO.getAddress(), operatorDTO.getEmail());
    }

    public Warehouse toWarehouse(WarehouseDTO dTO) {
        return new Warehouse(dTO.getWarehouseCode(), dTO.getUserId(), dTO.getWarehouseStatusDTO().getWhsId(), dTO.getLength(), dTO.getWidth(), dTO.getReseiveDate(), dTO.getDescription());
    }
    
    public WarehouseStatus toWarehouseStatus(WarehouseStatusDTO dTO) {
        return new WarehouseStatus(dTO.getWhsId(), dTO.getFullLength(), dTO.getFullWidth(), dTO.getFullPrice());
    }

    public WarehouseDTO fromWarehouse(Warehouse warehouse, WarehouseStatus status) {
        WarehouseStatusDTO warehouseStatusDTO = new WarehouseStatusDTO(status.getWhsId(), status.getFullLength(), status.getFullWidth(), status.getFullPrice());
        return new WarehouseDTO(warehouse.getWarehouseCode(), warehouse.getUserId(), warehouseStatusDTO, warehouse.getLength(), warehouse.getWidth(), warehouse.getReseiveDate(), warehouse.getDescription());
    }
    
    public Return toReturn(ReturnDTO returnDTO){
        return new Return(returnDTO.getWarehouseCode(), returnDTO.getRerurnedDate(), returnDTO.getPrice());
    }

    public User toUser(UserDTO userDTO) {
        return new User(userDTO.getUserId(), userDTO.getName(), userDTO.getAddress(), userDTO.getNic(), userDTO.getTelNumber(), userDTO.getEmail());
    }

    public UserDTO fromUser(User userDTO, List<WarehouseDTO> warehouseDTOs) {
        return new UserDTO(userDTO.getUserId(), userDTO.getName(), userDTO.getAddress(), userDTO.getNic(), userDTO.getTelNumber(), userDTO.getEmail(), warehouseDTOs);
    }

}
