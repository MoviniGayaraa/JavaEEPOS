package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.config.SessionFactoryConfig;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.CustomerDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerDAOImpl implements CustomerDAO<Customer, String> {

    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            String customerId = (String) session.save(dto);
            transaction.commit();

            if (customerId != null && !customerId.isEmpty()) {
                System.out.println("cus added");
                return true;
            } else {
                System.out.println("cus not added");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error add customer: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        try {
            Customer customer = session.get(Customer.class, id);
            session.close();
            return customer;
        } catch (Exception e) {
            System.out.println("Error Search customer: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        Session session = SessionFactoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, id);
            session.delete(customer);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error delete customer: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update (dto);
            transaction.commit ();
            session.close ();
            return true;
        }catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error update customer: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            Query<Customer> query = session.createQuery("SELECT C FROM Customer AS C");
            customers.addAll(query.getResultList());
            session.close();
            return customers;
        } catch (Exception e) {
            System.out.println("Error load all customers: " + e.getMessage());
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

