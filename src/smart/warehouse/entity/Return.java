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
public class Return implements SuperEntity{
    private String warehouseCode;
    private Date rerurnedDate;
    private double price;
}
