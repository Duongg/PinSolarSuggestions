package duongdd.servlet;

import duongdd.dao.PinsolarProductDAO;
import duongdd.entity.PinSolarProductEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PinSolarSuggestServlet extends HttpServlet {
    private static final String url = "pinsolar.jsp";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PinsolarProductDAO pinsolarProductDAO = new PinsolarProductDAO();
        List<PinSolarProductEntity> listPinSolar = new ArrayList<>();
        try{
            // list pin solar db
            listPinSolar = pinsolarProductDAO.getAllPinSolar();
            System.out.println(listPinSolar.size());
            // paging
            HttpSession session = request.getSession();
            if (request.getQueryString() != null && !request.getQueryString().contains("pageNumber")) {
                String queryString = "DispatcherServlet?" + request.getQueryString();
                session.setAttribute("QUERY", queryString);
            }
            String pageNum = request.getParameter("pageNumber");
            int pageNumber = 1;
            if(pageNum != null){
                pageNumber = Integer.parseInt(pageNum);
            }
            // divide 20 product per page
            int numberItems = listPinSolar.size();
            int maxPage = numberItems/20;
            if(numberItems % 20 != 0){
                maxPage = maxPage +1;
            }
            List<PinSolarProductEntity> resultPage = new ArrayList<>();
            // add product to one page
            if(listPinSolar != null){
                int fromPage = (pageNumber*20) - 20;
                int endPage = fromPage + 20;
                if(endPage < listPinSolar.size()){
                    for(int i = fromPage; i < endPage; i++){
                        resultPage.add(listPinSolar.get(i));
                    }
                }else {
                    for(int j = fromPage; j < listPinSolar.size(); j++){
                        resultPage.add(listPinSolar.get(j));
                    }
                }
            }
            String strMoney = request.getParameter("totalMoney");
            request.setAttribute("MONEY", strMoney);
            request.setAttribute("LISTPIN", resultPage);
            request.setAttribute("MAXPAGE", maxPage);
            request.setAttribute("PAGENUMBER", pageNumber);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
