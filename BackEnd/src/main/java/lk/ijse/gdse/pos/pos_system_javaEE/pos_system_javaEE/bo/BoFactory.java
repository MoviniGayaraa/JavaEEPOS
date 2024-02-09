package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl.OrderDetailsBOImpl;


public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {

    }

    public static BoFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDER,ORDER_DETAILS
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case ITEM:
                return (T) new ItemBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailsBOImpl();
            default:
                return null;
        }
    }

}
