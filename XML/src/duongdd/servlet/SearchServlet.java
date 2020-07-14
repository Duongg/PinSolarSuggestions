package duongdd.servlet;

import duongdd.dao.ElectrictProductDAO;
import duongdd.dtos.ProductDTO;
import duongdd.entity.ElectricProductEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private static final String HOME_ELECTRIC_PRODUCT = "homeElectricProduct.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = HOME_ELECTRIC_PRODUCT;
        ElectrictProductDAO electrictProductDAO = new ElectrictProductDAO();
        List<ElectricProductEntity> listProduct = new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            String nameCategory = request.getParameter("nameCategory");
            //list product db
            listProduct = electrictProductDAO.searchProductByBrand(nameCategory);
            if (nameCategory.equals("0")) {
                listProduct = electrictProductDAO.getAllElectricProduct();
                session.setAttribute("LISTELECTRICPRODUCT", listProduct);
            }
            if (request.getQueryString() != null && !request.getQueryString().contains("pageNumber")) {
                String queryString = "DispatcherServlet?" + request.getQueryString();
                session.setAttribute("QUERYSTRING", queryString);
            }
            String pageNum = request.getParameter("pageNumber");
            int pageNumber = 1;
            if (pageNum != null) {
                pageNumber = Integer.parseInt(pageNum);
            }
            // divide 20 product per one page
            int numberItems = listProduct.size();
            int maxPage = numberItems / 20;
            if (numberItems % 20 != 0) {
                maxPage = maxPage + 1;
            }
            List<ElectricProductEntity> resultPage = new ArrayList<>();
            // add product to one page
            if (listProduct != null) {
                int fromPage = (pageNumber * 20) - 20;
                int endPage = fromPage + 20;
                if (endPage < listProduct.size()) {
                    for (int i = fromPage; i < endPage; i++) {
                        resultPage.add(listProduct.get(i));
                    }
                } else {
                    for (int j = fromPage; j < listProduct.size(); j++) {
                        resultPage.add(listProduct.get(j));
                    }
                }
            }

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
