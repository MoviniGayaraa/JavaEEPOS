package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.OrderBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDTO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDetailsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/order")
public class OrderServlet extends HttpServlet {
    OrderBO orderBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ORDER);

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
        String id = req.getParameter("oid");
        System.out.println(id);
        Jsonb jsonb = JsonbBuilder.create();
        try {
            OrderDTO dto = orderBO.searchOrder(id);
            if(dto != null){
                jsonb.toJson(dto,resp.getWriter());
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
            ArrayList<OrderDTO> orderDto = orderBO.getAllOrder();

            if (orderDto != null) {
                System.out.println(orderDto+" getall");
                jsonb.toJson(orderDto, resp.getWriter());
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
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
        System.out.println("post method log");
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
        String orderId = orderDTO.getOid();
        String cusId = orderDTO.getCusID();
        List<OrderDetailsDTO> dto = orderDTO.getOrderDetails();

        if(orderId==null || !orderId.matches("OID-(0*[1-9]\\d{0,2})")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is empty or invalid");
            System.out.println("invalid order id");
            return;
        } else if (cusId == null || !cusId.matches("C00-(0*[1-9]\\d{0,2})")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID is empty or invalid");
            System.out.println("invalid cusid id");
            return;
        }
        for (OrderDetailsDTO list : dto) {
            String code = list.getItmCode();
            String price = String.valueOf(list.getItmPrice());
            String qty = String.valueOf(list.getItmQTY());
            if (code == null || !code.matches("I00-(0*[1-9]\\d{0,2})")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemCode is empty or invalid");
                return;
            } else if (!price.matches("[1-9]\\d*(\\.\\d+)?")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is empty or invalid");
                return;
            } else if (!qty.matches("[1-9]\\d*")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ItemQTY is empty or invalid");
                return;
            }
        }
        try {
            System.out.println("try catch");
            boolean isSaved = orderBO.saveOrder(orderDTO);
            if (isSaved){
                System.out.println("Order added");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else {
                System.out.println("Order not added");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException throwables) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}
