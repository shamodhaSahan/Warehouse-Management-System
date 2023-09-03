package smart.warehouse.entity;

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
public class WarehouseStatus implements SuperEntity{
    private String whsId;
    private double fullLength;
    private double fullWidth;
    private double fullPrice;
}
