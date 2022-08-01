import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {

    private String CONTENT_JSON_DIR;
    private String COMPANY_NAME;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        ServletContext context = getServletContext();
        CONTENT_JSON_DIR = context.getInitParameter("contentJsonPath");
        COMPANY_NAME = context.getInitParameter("companyName");
        log("DIR IS: " + CONTENT_JSON_DIR);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("jsonContentPath", CONTENT_JSON_DIR);
        request.setAttribute("companyName", COMPANY_NAME);
        log("DIR IS INDEDD: " + CONTENT_JSON_DIR);
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
    }
}