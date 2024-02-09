package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.ItemBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.ItemDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(value = "/item")
public class ItemServlet extends HttpServlet {
    ItemBO itemBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ITEM);

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
        String id = req.getParameter("itmCode");
        Jsonb jsonb = JsonbBuilder.create();
        try {
            ItemDTO item = itemBO.searchItem(id);
            if(item != null){
                jsonb.toJson(item,resp.getWriter());
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
            ArrayList<ItemDTO> itemAr = itemBO.getAllItems();

            if(itemAr != null){
                jsonb.toJson(itemAr,resp.getWriter());
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
        ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String code = item.getItmCode();
        String name = item.getItmName();
        String price = String.valueOf(item.getItmPrice());
        String qty = String.valueOf(item.getItmQTY());
        System.out.println(code+name+price+qty);
        if(code==null || !code.matches("I00-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemCode is empty or invalid");
            return;
        } else if (name == null || !name.matches("[A-Za-z ]{5,}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemName is empty or invalid");
            return;
        } else if ( !price.matches("[1-9]\\d*(\\.\\d+)?")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is empty or invalid");
            return;
        }
        else if (!qty.matches("[1-9]\\d*")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemQTY is empty or invalid");
            return;
        }
        try {
            boolean isSaved = itemBO.saveItem(new ItemDTO(code,name,item.getItmPrice(),item.getItmQTY()));
            if (isSaved){
                System.out.println("Item added");
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
        String code = req.getParameter("itmCode");
        if(code==null || !code.matches("I00-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemCode is empty or invalid");
            return;
        }
        try {
            boolean isDeleted = itemBO.deleteItem(code);
            if (isDeleted){
                System.out.println("Item Deleted");
                resp.setStatus(HttpServletResponse.SC_OK);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwable) {
            System.out.println("Sql error");
            throwable.printStackTrace();
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
        ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String code = item.getItmCode();
        String name = item.getItmName();
        String price = String.valueOf(item.getItmPrice());
        String qty = String.valueOf(item.getItmQTY());
        System.out.println(code+name+price+qty);
        if(code==null || !code.matches("I00-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemCode is empty or invalid");
            return;
        } else if (name == null || !name.matches("[A-Za-z ]{5,}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemName is empty or invalid");
            return;
        } else if ( !price.matches("[1-9]\\d*(\\.\\d+)?")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is empty or invalid");
            return;
        }
        else if (!qty.matches("[1-9]\\d*")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemQTY is empty or invalid");
            return;
        }
        try {
            System.out.println("put validation complete");
            boolean isSaved = itemBO.updateItem(new ItemDTO(code,name,item.getItmPrice(),item.getItmQTY()));
            if (isSaved){
                System.out.println("Item Update");
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

