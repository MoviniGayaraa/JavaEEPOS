package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.CustomerBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.CustomerDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(value = "/customer")
public class CustomerServlet extends HttpServlet {
    CustomerBO customerBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.CUSTOMER);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getParameter("info");

        if ((info.equals("getall"))) {
            getAll(resp);
        } else if ((info.equals("search"))) {
            search(req,resp);
        }

    }

    private void search(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("cusId");
        System.out.println(id);
            Jsonb jsonb = JsonbBuilder.create();
        try {
            CustomerDTO customer = customerBO.searchCustomer(id);
            if(customer != null){
                jsonb.toJson(customer,resp.getWriter());
                resp.setStatus(HttpServletResponse.SC_OK);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
    private void getAll(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        try {
            ArrayList<CustomerDTO> customerAr = customerBO.getAllCustomers();

            if(customerAr != null){
                jsonb.toJson(customerAr,resp.getWriter());
                resp.setStatus(HttpServletResponse.SC_OK);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        System.out.println("post method");
        CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        String id = customer.getCusID();
        String name = customer.getCusName();
        String address = customer.getCusAddress();
        System.out.println(jsonb);
        if(id==null || !id.matches("C00-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is empty or invalid");
            return;
        } else if (name == null || !name.matches("[A-Za-z ]{5,}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty or invalid");
            return;
        } else if (address == null || address.length() < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address is empty or invalid");
            return;
        }
        System.out.println(customer);
        try {
            boolean isSaved = customerBO.saveCustomer(new CustomerDTO(id,name,address));
            if (isSaved){
                System.out.println("Customer added");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }
        } catch (SQLException throwables) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusId");
        if(cusID==null || !cusID.matches("C00-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is empty or invalid");
            return;
        }
        try {
            boolean isDeleted = customerBO.deleteCustomer(cusID);
            if (isDeleted){
                System.out.println("Customer Deleted");
                resp.setStatus(HttpServletResponse.SC_OK);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwables) {
            System.out.println("Sql error");
            throwables.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e) {
            System.out.println("Class error");
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("put invoke");
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        String id = customer.getCusID();
        String name = customer.getCusName();
        String address = customer.getCusAddress();

        if(id==null || !id.matches("C00-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is empty or invalid");
            return;
        } else if (name == null || !name.matches("[A-Za-z ]{5,}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is empty or invalid");
            return;
        } else if (address == null || address.length() < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address is empty or invalid");
            return;
        }
        try {
            System.out.println("put validation complete");
            boolean isSaved = customerBO.updateCustomer(new CustomerDTO(id,name,address));
            if (isSaved){
                System.out.println("Customer Update");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwables) {
            System.out.println("Sql error");
            throwables.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e) {
            System.out.println("Class error");
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
