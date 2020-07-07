package duongdd.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
    private static final String ERROR = "error.jsp";
    private static final String HOMEELECTRICPRODCUT ="HomeElectricProductServlet";
    private static final String ADDTOCACULATE = "AddToCaculateServlet";
    private static final String CACULATE = "CaculateMoneyServlet";
    private static final String REMOVEITEM = "RemoveItemServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            String action = request.getParameter("btAction");
            if (action == null) {
                url = HOMEELECTRICPRODCUT;
            }else if(action.equals("Add")){
                url = ADDTOCACULATE;
            }else if(action.equals("Caculate Electric Money")){
                url = CACULATE;
            }else if(action.equals("Remove")){
                url = REMOVEITEM;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
