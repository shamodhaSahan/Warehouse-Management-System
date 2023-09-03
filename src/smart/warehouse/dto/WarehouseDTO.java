package smart.warehouse.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WarehouseDTO{
    private String warehouseCode;
    private String userId;
    private WarehouseStatusDTO warehouseStatusDTO;
    private double length;
    private double width;
    private Date reseiveDate;
    private String description;
}
