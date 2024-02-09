package lk.ijse.gdse.pos.pos_system_javaEE.pos_system_javaEE.api.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CORSFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
//        System.out.println("Core filter Response in");
        res.addHeader("Access-Control-Allow-Origin","*");
        res.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS");
        res.addHeader("Access-Control-Allow-Headers","Content-Type");
        chain.doFilter(req, res);
//        System.out.println("Core filter Response out");
    }
}
