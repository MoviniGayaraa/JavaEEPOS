package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.impl;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.config.SessionFactoryConfig;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.ItemDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;


public class ItemDAOImpl<T, ID> implements ItemDAO<Item, String> {

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            String save = (String) session.save(dto);
            transaction.commit();

            if (save != null && !save.isEmpty()) {
                System.out.println("item added");
                return true;
            } else {
                System.out.println("item not added");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error adding item: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        try {
            Item item = session.get(Item.class, id);
            session.close();
            return item;
        } catch (Exception e) {
            System.out.println("Error search Item: " + e.getMessage());
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
            Item item = session.get(Item.class, id);
            session.delete(item);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error delete Item: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
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
            System.out.println("Error update item: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        Session session = SessionFactoryConfig.getSession();
        ArrayList<Item> items = new ArrayList<>();
        try {
            Query<Item> query = session.createQuery("SELECT C FROM Item AS C");
            items.addAll(query.getResultList());
            session.close();
            return items;
        } catch (Exception e) {
            System.out.println("Error load all items: " + e.getMessage());
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
