package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity;


import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "oid")
    private String oid;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "cusID", nullable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderDetails> orderDetails = new ArrayList<>();

    public OrderDTO toDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOid(this.oid);
        orderDTO.setDate(this.date);
        orderDTO.setCusID(this.customer.getCusID());
        return orderDTO;
    }

}
