package duongdd.servlet;

import duongdd.dao.BrandProductDAO;
import duongdd.dao.CategoryProductDAO;
import duongdd.dao.ElectrictProductDAO;
import duongdd.dtos.ProductDTO;
import duongdd.entity.BrandProductEntity;
import duongdd.entity.CategoryProductEntity;
import duongdd.entity.ElectricProductEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAddFormServlet extends HttpServlet {
    private static final String ADD_NEW_PRODUCT = "addNewProduct.jsp";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<CategoryProductEntity> listCategory = new ArrayList<>();
        List<BrandProductEntity> listBrand = new ArrayList<>();
        List<ElectricProductEntity> listProduct = new ArrayList<>();
        CategoryProductDAO categoryProductDAO = new CategoryProductDAO();
        BrandProductDAO brandProductDAO = new BrandProductDAO();

        String url = ADD_NEW_PRODUCT;
        try {


            //list category db
            listCategory = categoryProductDAO.getAllNameCategory();
            //list brand db
            listBrand = brandProductDAO.getAllNameBrand();
            request.setAttribute("LISTCATEGORY", listCategory);
            request.setAttribute("LISTBRAND", listBrand);




        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
