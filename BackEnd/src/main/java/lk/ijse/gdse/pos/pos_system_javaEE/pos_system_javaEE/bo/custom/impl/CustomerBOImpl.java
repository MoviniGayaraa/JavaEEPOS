package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.CustomerBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dao.custom.CustomerDAO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.CustomerDTO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(dto.toEntity());
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = (Customer) customerDAO.search(id);
        if(customer != null) {
            return customer.toDTO();
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(dto.toEntity());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> cusDTO = new ArrayList<>();

        for (Customer c : all) {
            CustomerDTO customerDTO = c.toDTO();
            cusDTO.add(customerDTO);
        }
        return cusDTO;
    }

    @Override
    public boolean CustomerExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
