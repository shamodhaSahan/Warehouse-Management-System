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
public class Operator implements SuperEntity{
    private String operatorId;    
    private String operatorName;
    private String password;
    private String nic;
    private String address;
    private String email;
}
