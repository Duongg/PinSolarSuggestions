package duongdd.servlet;

import duongdd.dtos.ProductCart;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AddToCaculateServlet extends HttpServlet {
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
            String id = request.getParameter("idProduct");
            String nameCate = request.getParameter("nameCategory");

            String pageNumber = request.getParameter("pageNumber");
            int idProduct = Integer.parseInt(id);
            if (request.getQueryString() != null
                    && nameCate != null
                    && !nameCate.equals("")
                    && !request.getQueryString().contains("pageNumber")
                    && !nameCate.contains(" ")
                    && !nameCate.equals("")
            ) {
                url = "DispatcherServlet?nameCategory=" + nameCate + "&btAction=Search&pageNumber=" + pageNumber;

            }
            cart.addItemsToCaculate(idProduct);
            session.setAttribute("CART", cart);
            session.setAttribute("QUERYSTRING", url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
