package duongdd.servlet;

import duongdd.dao.BrandProductDAO;
import duongdd.dao.CategoryProductDAO;
import duongdd.dao.ElectrictProductDAO;
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


public class AddNewProductServlet extends HttpServlet {
    private static final String ADDNEWPRODUCT = "AddNewProductServlet";
    private static final String HOMESERVLET = "HomeElectricProductServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryProductEntity> listCategory = new ArrayList<>();
        List<BrandProductEntity> listBrand = new ArrayList<>();
        ElectrictProductDAO electrictProductDAO = new ElectrictProductDAO();
        ElectricProductEntity productEntity = new ElectricProductEntity();
        CategoryProductDAO categoryProductDAO = new CategoryProductDAO();
        BrandProductDAO brandProductDAO = new BrandProductDAO();
        String url = ADDNEWPRODUCT;
        try {
            //list category db
            listCategory = categoryProductDAO.getAllCategory();
            //list brand db
            listBrand = brandProductDAO.getAllBrand();

            String nameProduct = request.getParameter("productName");
            String capacityProduct = request.getParameter("capacityProduct");
            String brandName = request.getParameter("brandProduct");
            String categoryName = request.getParameter("nameCategory");

            double capacity = 0;
            if (capacityProduct != null && !capacityProduct.equals("")) {
                capacity = Float.parseFloat(capacityProduct);
            }

            productEntity.setProductName(nameProduct);
            productEntity.setProductCapacity(capacity);
            if(brandName == null && categoryName == null){
                for (int i = 0; i < listCategory.size(); i++) {
                    if (categoryName.equals(listCategory.get(i).getNameCategory())) {
                        productEntity.setIdCategory(listCategory.get(i).getIdCategory());
                    }
                }
                for (int j = 0; j < listBrand.size(); j++) {
                    if (brandName.equals(listBrand.get(j).getNameBrand())) {
                        productEntity.setIdBrand(listBrand.get(j).getIdBrandProduct());
                    }
                }
            }


            electrictProductDAO.insertProduct(productEntity);
            url = HOMESERVLET;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
