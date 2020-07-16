package duongdd.servlet;

import duongdd.dtos.PinSolarCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RemovePinServlet")
public class RemovePinServlet extends HttpServlet {
    private static String url = "detailPinsolar.jsp";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            PinSolarCart cart = (PinSolarCart) session.getAttribute("CARTPIN");
            if(cart == null){
                cart = new PinSolarCart();
            }
            if(cart != null){
                String id = request.getParameter("idPinsolar");
                if(id != null){
                    int idProduct = Integer.parseInt(id);
                    cart.removeItem(idProduct);
                }
                session.setAttribute("CARTPIN",cart);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
