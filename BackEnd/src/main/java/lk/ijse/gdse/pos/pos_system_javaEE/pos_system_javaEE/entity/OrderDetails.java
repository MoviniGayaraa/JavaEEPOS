package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDetailsDTO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.embedded.OrderDetailPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetails {
    @EmbeddedId
    private OrderDetailPK orderDetailPK;

    @Column(name = "itmQTY")
    private int itmQTY;

    @ManyToOne
    @JoinColumn(name = "oid", referencedColumnName = "oid", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "itmCode", referencedColumnName = "itmCode", insertable = false, updatable = false)
    private Item item;


    public OrderDetailsDTO toDto() {
        OrderDetailsDTO details = new OrderDetailsDTO();
        details.setOid(String.valueOf(this.orderDetailPK.getOid()));
        details.setItmCode(this.item.getItmCode());
        details.setItmQTY(this.itmQTY);
        details.setItmPrice(this.item.getItmPrice());
        return details;
    }

}

