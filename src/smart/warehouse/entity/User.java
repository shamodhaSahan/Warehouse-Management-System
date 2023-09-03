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
public class User  implements SuperEntity{
    private String userId;
    private String name;
    private String address;
    private String nic;
    private String telNumber;
    private String email;
}
