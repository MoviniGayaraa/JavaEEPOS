package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDetailsDTO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDetailsBOImpl implements OrderDetailsBO {
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll();
        ArrayList<OrderDetailsDTO> orderAr = new ArrayList<>();

        for (OrderDetails order : all) {
            OrderDetailsDTO dto = order.toDto();
            orderAr.add(dto);
        }
        return orderAr;
    }
}
