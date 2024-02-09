package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Item;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Order;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private String oid;
    private String itmCode;
    private int itmQTY;
    private double itmPrice;

    public OrderDetails toEntity(){
        OrderDetails details = new OrderDetails();
        Item item = new Item();
        item.setItmCode(this.itmCode);
        item.setItmPrice(this.itmPrice);
        details.setItem(item);
        details.setItmQTY(this.itmQTY);

        Order order = new Order();
        order.setOid(this.oid);
        details.setOrder(order);
        return details;
    }
}
