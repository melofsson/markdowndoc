
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



@WebServlet("/videoReaderServlet")
public class VideoReaderServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String video_dir = context.getInitParameter("videoPath");
        String fileName = request.getParameter(("filename"));
        String fileDir = request.getParameter(("filedir"));
        response.setContentType("video/mp4");
        ServletOutputStream out = response.getOutputStream();
        FileInputStream fin = new FileInputStream(video_dir + fileDir + "/"+fileName);

        byte [] buf = new byte[4096];
        int read;
        while((read = fin.read(buf)) != -1)
        {
            out.write(buf, 0, read);
        }

        fin.close();
        out.flush();
        out.close();

    }

}