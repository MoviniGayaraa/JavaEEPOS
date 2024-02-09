package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.dto.OrderDetailsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(value = "/orderDetails")
public class OrderDetailsServlet extends HttpServlet {
    OrderDetailsBO detailsBO = BoFactory.getBoFactory().getBO(BoFactory.BOTypes.ORDER_DETAILS);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        try {
            ArrayList<OrderDetailsDTO> orderDto = detailsBO.getAllOrderDetails();

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
}
