package smart.warehouse.bo.custom;

import java.util.List;
import smart.warehouse.bo.SuperBO;
import smart.warehouse.bo.exception.NotFoundException;
import smart.warehouse.dto.ReturnDTO;
import smart.warehouse.dto.WarehouseDTO;
import smart.warehouse.dto.WarehouseStatusDTO;

public interface StorageBO extends SuperBO{
    public String getNextWareHouseId();
    public WarehouseStatusDTO getCurrentStatus();
    public Boolean addWarehouse(WarehouseDTO warehouseDTO);
    public WarehouseDTO searchWarehouse(String warehouseCode) throws NotFoundException;
    public List<String> getAllReturnedWarehouseID();
    public List<String> getAllNonReturnedWarehouseID();
    public Boolean deleteStorage(WarehouseDTO warehouseDTO);
    public Boolean returnWarehouse(List<ReturnDTO> returnDTO);
    public Boolean isStatusEmpty();
    public Boolean saveStatus(WarehouseStatusDTO warehouseStatusDTO);
}
