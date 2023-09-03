package smart.warehouse.entity;

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
public class Warehouse implements SuperEntity{
    private String warehouseCode;
    private String userId;
    private String whsId;
    private double length;
    private double width;
    private Date reseiveDate;
    private String description;
}
