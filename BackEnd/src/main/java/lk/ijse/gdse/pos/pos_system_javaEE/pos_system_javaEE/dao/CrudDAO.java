package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO{
    boolean save(T dto) throws SQLException, ClassNotFoundException ;


    T search(ID s) throws SQLException, ClassNotFoundException ;


    boolean delete(ID s) throws SQLException, ClassNotFoundException ;

    boolean update(T dto) throws SQLException, ClassNotFoundException ;


    ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;


    boolean exist(ID s) throws SQLException, ClassNotFoundException ;
}
