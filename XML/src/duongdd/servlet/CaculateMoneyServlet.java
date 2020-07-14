package duongdd.servlet;

import duongdd.dtos.ProductCart;
import duongdd.entity.ElectricProductEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CaculateMoneyServlet extends HttpServlet {
    private static String HOMEELECTRICPRODUCT = "homeElectricProduct.jsp";

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
            String[] strQuantity = request.getParameterValues("txtQuantity");
            String[] strHour = request.getParameterValues("txtTime");

            String idProduct = request.getParameter("idProduct");
            int id = Integer.parseInt(idProduct);


            int totalMoney = 0;
            float useCapacity = 0;
            int totalCapacity = 0;
            int quantity = 0;
            float time = 0;
            String strCapacity = "";
            String strTotalMoney = "";
            if (cart != null) {
                Map<Integer, ElectricProductEntity> items = cart.getItems();
                if (items != null) {
                    float totalCapacityOneDay = 0;
                    int count = 0;
                    for (Map.Entry<Integer, ElectricProductEntity> entry : items.entrySet()) {

                        String quan = strQuantity[count];
                        quantity = Integer.parseInt(quan);
                        String hour = strHour[count];
                        time = Float.parseFloat(hour);

                        ElectricProductEntity value = entry.getValue();
                        useCapacity = (float) (value.getProductCapacity() * quantity * time);

                        totalCapacityOneDay = totalCapacityOneDay + useCapacity;
                        count++;
                    }
                    totalCapacity = (int) (totalCapacityOneDay * 30);
                    totalMoney = (int) cart.caculateElectric(totalCapacity);
                    strTotalMoney = String.format("%d", totalMoney);
                    strCapacity = String.format("%d", totalCapacity);
                }
            }

            session.setAttribute("CAPA", strCapacity);
            session.setAttribute("MONEY", strTotalMoney);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }


    }
}
