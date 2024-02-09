package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException ;

    boolean OrderExist(String id) throws SQLException, ClassNotFoundException;
}
