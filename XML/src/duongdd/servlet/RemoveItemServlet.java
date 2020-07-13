package duongdd.servlet;

import duongdd.dtos.ProductCart;
import duongdd.entity.ElectricProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "RemoveItemServlet")
public class RemoveItemServlet extends HttpServlet {
    private static String HOMEELECTRICPRODUCT = "HomeElectricProductServlet";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = HOMEELECTRICPRODUCT;
        try {

            HttpSession session = request.getSession();
            ProductCart cart = (ProductCart) session.getAttribute("CART");
            if (cart == null) {
                cart = new ProductCart();
            }
            if (cart != null) {
                String id = request.getParameter("idProduct");
                if (id != null) {
                    int idProduct = Integer.parseInt(id);
                    cart.removeItem(idProduct);
                }
                session.setAttribute("CART", cart);
            }
            url = HOMEELECTRICPRODUCT;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
