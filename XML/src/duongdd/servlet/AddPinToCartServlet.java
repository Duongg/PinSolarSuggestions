package duongdd.servlet;

import duongdd.dtos.PinSolarCart;
import duongdd.dtos.ProductCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddPinToCartServlet extends HttpServlet {
    private static String CARTPIN = "PinSolarSuggestServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = CARTPIN;
        try {
            HttpSession session = request.getSession();
            PinSolarCart cart = (PinSolarCart) session.getAttribute("CARTPIN");
            if (cart == null) {
                cart = new PinSolarCart();
            }
            String id = request.getParameter("idPinSolar");
            String pinNumber = request.getParameter("pinNumber");
            int idProduct = Integer.parseInt(id);
            String pageNumber = request.getParameter("pageNumber");
            if(request.getQueryString() != null && !request.getQueryString().contains("pageNumber")){
                url = "DispatcherServlet?&btAction=Add+Pin&idProduct=" + id + "&pageNumber="+ pageNumber;
            }
            cart.addItemsToCart(idProduct);

            session.setAttribute("CARTPIN", cart);
            session.setAttribute("PINNUM", pinNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
