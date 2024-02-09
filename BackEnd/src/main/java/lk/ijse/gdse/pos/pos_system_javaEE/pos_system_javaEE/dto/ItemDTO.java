package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String itmCode;
    private String itmName;
    private double itmPrice;
    private int itmQTY;

    public Item toEntity(){
        Item item = new Item();
        item.setItmCode(this.itmCode);
        item.setItmName(this.itmName);
        item.setItmPrice(this.itmPrice);
        item.setItmQTY(this.itmQTY);
        return item;
    }
}
