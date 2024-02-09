package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {

    ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException ;

}
