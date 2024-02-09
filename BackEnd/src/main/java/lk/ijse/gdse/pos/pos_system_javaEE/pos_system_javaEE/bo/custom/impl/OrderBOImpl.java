package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.OrderBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.config.SessionFactoryConfig;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.ItemDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDTO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDetailsDTO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Item;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        System.out.println("Bo save method");
        Session session = SessionFactoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            System.out.println(dto);
            session.save(dto.toEntity());
            for (OrderDetailsDTO dtos: dto.getOrderDetails()) {
                Item item = session.get(Item.class, dtos.getItmCode());
                if (item != null) {
                    int current = item.getItmQTY();
                    int ordered = dtos.getItmQTY();
                    item.setItmQTY(current - ordered);
                } else {
                    System.out.println("Item not found");
                    session.getTransaction().rollback();
                    return false;
                }
                session.update(item);
            }

            transaction.commit();
            System.out.println("order added");
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error add order: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }


    @Override
    public OrderDTO searchOrder(String id) throws SQLException, ClassNotFoundException {
        Order order = (Order) orderDAO.search(id);
        if (order != null) {
            return order.toDTO();
        }
        return null;
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<Order> all = orderDAO.getAll();
        ArrayList<OrderDTO> orderAr = new ArrayList<>();

        for (Order order : all) {
            OrderDTO dto = order.toDTO();
            orderAr.add(dto);
        }
        return orderAr;
    }

    @Override
    public boolean OrderExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
