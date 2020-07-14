package duongdd.servlet;

import duongdd.dao.BrandProductDAO;
import duongdd.dao.CategoryProductDAO;
import duongdd.dao.ElectrictProductDAO;
import duongdd.dtos.ProductDTO;
import duongdd.entity.BrandProductEntity;
import duongdd.entity.CategoryProductEntity;
import duongdd.entity.ElectricProductEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeElectricProductServlet extends HttpServlet {
    private static final String HOME_ELECTRIC_PRODUCT = "homeElectricProduct.jsp";
    private static final String ERROR = "error.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ElectrictProductDAO electrictProductDAO = new ElectrictProductDAO();
        CategoryProductDAO categoryProductDAO = new CategoryProductDAO();
        List<ElectricProductEntity> listElectricProduct = new ArrayList<>();
        List<CategoryProductEntity> listCategory = new ArrayList<>();
        String url = ERROR;

        try {
            // get list category
            listCategory = categoryProductDAO.getAllNameCategory();
            //search product by category
            String nameCategory = request.getParameter("nameCategory");
            if (nameCategory != null && !nameCategory.equals("")) {
                listElectricProduct = electrictProductDAO.searchProductByBrand(nameCategory);
            } else {
                // get all product from DB
                listElectricProduct = electrictProductDAO.getAllElectricProduct();
            }

            // get url 1 page
            HttpSession session = request.getSession();

//            if (request.getQueryString() != null && !request.getQueryString().contains("pageNumber")) {
//                String queryString = "DispatcherServlet?" + request.getQueryString();
                session.setAttribute("QUERYSTRING", "DispatcherServlet?&btAction=Home");
//            }

            // paging
            String pageNum = request.getParameter("pageNumber");
            int pageNumber = 1;
            if (pageNum != null) {
                pageNumber = Integer.parseInt(pageNum);
            }
            // divide 20 product per one page
            int numberItems = listElectricProduct.size();
            int maxPage = numberItems / 20;
            if (numberItems % 20 != 0) {
                maxPage = maxPage + 1;
            }
            List<ElectricProductEntity> resultPage = new ArrayList<>();
            // add product to one page
            if (listElectricProduct != null) {
                int fromPage = (pageNumber * 20) - 20;
                int endPage = fromPage + 20;
                if (endPage < listElectricProduct.size()) {
                    for (int i = fromPage; i < endPage; i++) {
                        resultPage.add(listElectricProduct.get(i));
                    }
                } else {
                    for (int j = fromPage; j < listElectricProduct.size(); j++) {
                        resultPage.add(listElectricProduct.get(j));
                    }
                }
            }

            session.setAttribute("LISTCATE", listCategory);
            session.setAttribute("LISTELECTRICPRODUCT", resultPage);
            request.setAttribute("MAXPAGE", maxPage);
            request.setAttribute("PAGENUMBER", pageNumber);
            url = HOME_ELECTRIC_PRODUCT;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }
}
