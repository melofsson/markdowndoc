
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



@WebServlet("/imageReaderServlet")
public class ImageReaderServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log("LOGO" + request.getParameter("img"));
        ServletContext context = getServletContext();
        String imgPath = "";
        String logoPath = context.getInitParameter("logoPath");
        if (request.getParameter("img").equalsIgnoreCase("logo")) {
            imgPath = logoPath;
        }
        response.setContentType("image/png");  
        ServletOutputStream out;  
        out = response.getOutputStream();  
        FileInputStream fin = new FileInputStream(imgPath);  
        BufferedInputStream bin = new BufferedInputStream(fin);  
        BufferedOutputStream bout = new BufferedOutputStream(out);  
        int ch =0; ;  
        while((ch=bin.read())!=-1)  
        {  
        bout.write(ch);  
        }  
        
        bin.close();  
        fin.close();  
        bout.close();  
        out.close();  

    }

}