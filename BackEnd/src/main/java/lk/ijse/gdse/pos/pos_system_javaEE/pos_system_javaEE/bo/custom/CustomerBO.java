package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException ;

    boolean CustomerExist(String id) throws SQLException, ClassNotFoundException;
}
