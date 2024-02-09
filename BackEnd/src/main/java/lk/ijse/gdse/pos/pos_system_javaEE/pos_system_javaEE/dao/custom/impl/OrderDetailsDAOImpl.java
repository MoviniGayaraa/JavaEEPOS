package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.config.SessionFactoryConfig;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.OrderDetails;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl<T,ID> implements OrderDetailsDAO<OrderDetails,String> {
    @Override
    public boolean save(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        ArrayList<OrderDetails> orders = new ArrayList<>();
        try {
            Query<OrderDetails> query = session.createQuery("SELECT O FROM OrderDetails AS O");

            orders.addAll(query.getResultList());
            session.close();
            return orders;
        } catch (Exception e) {
            System.out.println("Error load all orders: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }
}

