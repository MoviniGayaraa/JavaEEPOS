package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String cusID;
    private String cusName;
    private String cusAddress;

    public Customer toEntity(){
        Customer customer = new Customer();
        customer.setCusID(this.cusID);
        customer.setCusName(this.cusName);
        customer.setCusAddress(this.cusAddress);
        return customer;
    }
}
