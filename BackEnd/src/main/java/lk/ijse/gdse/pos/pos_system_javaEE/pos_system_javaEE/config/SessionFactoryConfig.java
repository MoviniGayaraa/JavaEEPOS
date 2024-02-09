package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.config;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Customer;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Item;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Order;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.OrderDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class SessionFactoryConfig {
    private static SessionFactory sessionFactory;
    static {
        sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder().loadProperties("hibernate.cfg.properties").build())
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetails.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build()
                .buildSessionFactory();
    }

    private SessionFactoryConfig() {
    }
    public static SessionFactory getInstance(){
        return sessionFactory;
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
