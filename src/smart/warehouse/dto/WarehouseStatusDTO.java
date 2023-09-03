package smart.warehouse.dto;

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
public class WarehouseStatusDTO{
    private String whsId;
    private double fullLength;
    private double fullWidth;
    private double fullPrice;
}
