package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl.OrderDetailsDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDAOFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER, ITEM, ORDER,ORDER_DETAILS
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailsDAOImpl();
            default:
                return null;
        }
    }
}
