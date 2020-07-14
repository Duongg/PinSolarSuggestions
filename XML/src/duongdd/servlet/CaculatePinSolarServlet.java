package duongdd.servlet;

import duongdd.dao.PinsolarProductDAO;
import duongdd.entity.PinSolarProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CaculatePinSolarServlet extends HttpServlet {
    private static String url = "PinSolarSuggestServlet";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PinSolarProductEntity pinSolarProductEntity = new PinSolarProductEntity();
            PinsolarProductDAO pinsolarProductDAO = new PinsolarProductDAO();

            float money = 0;
            float totalCapacity = 0;
            float pvpanel = 0;
            float totalWp = 0;
            float capacityPV = 0;
            int pinNumber = 0;

            String strIdPinSolar = request.getParameter("idPinsolar");
            String strMoney = request.getParameter("totalMoney");
            String strCapacity = request.getParameter("txtCapacity");
            if (strMoney != null && !strMoney.equals("")) {
                money = Float.parseFloat(strMoney);
            }
            if (strCapacity != null && !strCapacity.equals("")) {
                capacityPV = Float.parseFloat(strCapacity);
            }
            int idPinSolar = Integer.parseInt(strIdPinSolar);
            pinSolarProductEntity = pinsolarProductDAO.getPinDetail(idPinSolar);

            // total capacity sell 2000 / kwh
            totalCapacity = (money / 2000);
            //
            pvpanel = (float) ((totalCapacity * 1.3 * 1000)/30);
            // he so muc do hap thu nang luong mat troi VN
            totalWp = (float) (pvpanel / 4.6);
            // get number pin
            pinNumber = (int) (totalWp / capacityPV);
            if((totalWp % capacityPV)>0){
                pinNumber = pinNumber + 1;
            }
            request.setAttribute("PRODUCTDETAIL",pinSolarProductEntity);
            request.setAttribute("PINNUMBER", pinNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
