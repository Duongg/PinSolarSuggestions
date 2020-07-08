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
    private static String HOMEELECTRICPRODUCT = "HomeElectricProductServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String url = HOMEELECTRICPRODUCT;
        try {

            HttpSession session = request.getSession();
            ProductCart cart = (ProductCart) session.getAttribute("CART");
            if(cart == null){
                cart = new ProductCart();
            }
           String strQuantity = request.getParameter("txtQuantity");
           String strHour = request.getParameter("txtTime");
           String idProduct = request.getParameter("idProduct");
            int id = Integer.parseInt(idProduct);
            int quantity = Integer.parseInt(strQuantity);
            float time = Float.parseFloat(strHour);
            int totalMoney = 0;
            float useCapacity = 0;
            int capacity = 0;
            String strCapacity = "";
            String strTotalMoney = "";
            if(cart != null){
                Map<Integer, ElectricProductEntity> items = cart.getItems();
                if(items != null){
                    for(Map.Entry<Integer, ElectricProductEntity> entry : items.entrySet()){
                        ElectricProductEntity value = entry.getValue();
                        useCapacity += (float) (value.getProductCapacity() * quantity * time);
                        capacity = (int) (useCapacity * 30);
                        totalMoney = (int) cart.caculateElectric(id, capacity);
                        strTotalMoney = String.format("%,d",totalMoney);
                        strCapacity = String.format("%,d",capacity);
                    }
                }
            }
            System.out.println(strTotalMoney);
            session.setAttribute("CAPA", strCapacity);
           session.setAttribute("MONEY", strTotalMoney);
       }catch (Exception e){
           System.out.println(e.getMessage());
       }finally {
           request.getRequestDispatcher(url).forward(request, response);
       }


    }
}
