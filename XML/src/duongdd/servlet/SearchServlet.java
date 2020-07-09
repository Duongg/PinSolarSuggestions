package duongdd.servlet;

import duongdd.dao.ElectrictProductDAO;
import duongdd.dtos.ProductDTO;
import duongdd.entity.ElectricProductEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private static final String HOME_ELECTRIC_PRODUCT = "HomeElectricProductServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String url = HOME_ELECTRIC_PRODUCT;
        ElectrictProductDAO electrictProductDAO = new ElectrictProductDAO();
        List<ElectricProductEntity> listProduct = new ArrayList<>();
            try{
                String nameCategory = request.getParameter("nameCategory");
                listProduct = electrictProductDAO.searchProductByBrand(nameCategory);
                request.setAttribute("LISTELECTRICPRODUCT", listProduct);
                url = HOME_ELECTRIC_PRODUCT;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                request.getRequestDispatcher(url).forward(request,response);
            }
    }
}
