package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.CustomerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "cusID")
    private String cusID;
    @Column(name = "cusName")
    private String cusName;
    @Column(name = "cusAddress")
    private String cusAddress;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer(String cusID, String cusName, String cusAddress) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
    }

    public CustomerDTO toDTO(){
        CustomerDTO cusDTO = new CustomerDTO();
        cusDTO.setCusID(this.cusID);
        cusDTO.setCusName(this.cusName);
        cusDTO.setCusAddress(this.cusAddress);
        return cusDTO;
    }
}
