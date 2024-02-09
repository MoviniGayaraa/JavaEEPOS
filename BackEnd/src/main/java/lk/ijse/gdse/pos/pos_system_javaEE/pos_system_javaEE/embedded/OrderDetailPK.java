package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrderDetailPK implements Serializable {
    @Column(name = "oid")
    private String oid;
    @Column(name = "itmCode")
    private String itmCode;
}
