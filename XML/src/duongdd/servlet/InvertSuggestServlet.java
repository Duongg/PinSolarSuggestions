package duongdd.servlet;

import duongdd.dao.PinsolarProductDAO;
import duongdd.entity.PinSolarProductEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvertSuggestServlet extends HttpServlet {
    String url = "CaculatePinSolarServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PinsolarProductDAO pinsolarProductDAO = new PinsolarProductDAO();
        List<PinSolarProductEntity> listInverter = new ArrayList<>();
            try{
                int numberPin = 0;
                int capacityPin = 0;
                int totalInverter = 0;
                float capacity = 0;
                String pinNumber = request.getParameter("pinNumber");
                String strPinCapacity = request.getParameter("capacityPin");
                int pos = strPinCapacity.indexOf(".0");
                String strCapacity = strPinCapacity.substring(0, pos);
                if(pinNumber != null){
                    numberPin = Integer.parseInt(pinNumber);
                }
                if(strCapacity != null){
                    capacityPin = Integer.parseInt(strCapacity);
                }
                capacity = (float) (capacityPin * 0.001);
                totalInverter = (int) ((numberPin * capacity) / 1.2);
                if(((numberPin * capacity) % 1.2) > 0){
                    totalInverter = totalInverter + 1;
                }
                listInverter = pinsolarProductDAO.searchInverter(totalInverter*1000);
                request.setAttribute("INVERTER", listInverter);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                request.getRequestDispatcher(url).forward(request,response);
            }
    }
}
