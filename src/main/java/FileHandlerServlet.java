import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import beans.ContentJsonFileHandler;
import pojos.Page;
import pojos.Section;


@WebServlet("/fileHandlerServlet")
public class FileHandlerServlet extends HttpServlet {

    private String sectionDirName;
    private String pageFileName;
    private ContentJsonFileHandler jsonFileHandler;
    private String CONTENT_JSON_DIR;
    private String PAGES_DIR;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        ServletContext context = getServletContext();
        CONTENT_JSON_DIR = context.getInitParameter("contentJsonPath");
        PAGES_DIR = context.getInitParameter("pagesPath");
        jsonFileHandler = new ContentJsonFileHandler(CONTENT_JSON_DIR);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = "";
        String destPath = "";
        String section = "";
        String pageName = "";
        String parentPageName = "";
        String parentPageDir = "";
        String subPageName = "";
        
        if (isCreateNewPageRequest(request) && !isSubPageRequest(request)) {
            fileName = pageFileName;
            pageName = request.getParameter("pageName");
            section = getSection(request);
            destPath = "editpage";
        } else if (isDeletePageRequest(request)) {
            destPath = request.getParameter("destPath");
        } else if (isSubPageRequest(request)) {
            destPath = request.getParameter("destPath");
            if (isCreateNewPageRequest(request)){
                fileName = pageFileName;
                destPath = "editpage";
            } else {
                fileName = request.getParameter("filename");
            } 
            parentPageDir = request.getParameter("parentPageDir");
            subPageName = request.getParameter("pageName");
            parentPageName = request.getParameter("parentPageName");
            section = request.getParameter("section");
        } else {
            fileName = request.getParameter("filename");
            pageName = request.getParameter("pageName");
            destPath = request.getParameter("destPath");
            section = request.getParameter("section");
        }
        if (!isDeletePageRequest(request)) {
            if (isSubPageRequest(request) && !isDeleteSubPageRequest(request)){
                sectionDirName = section.replace(" ", "_").toLowerCase();
                String data = Files.lines(Paths.get(PAGES_DIR + sectionDirName + "/" + parentPageDir  + "/" + fileName+ ".md"))
                .collect(Collectors.joining(System.lineSeparator()));
                
                
                /*Files.readString(Path.of(PAGES_DIR + sectionDirName + "/" + parentPageDir  + "/" + fileName+ ".md"));*/
                request.setAttribute("section",section);
        
                HashMap parentPageBeanMap = new HashMap<>();
                parentPageBeanMap.put("name", parentPageName);
                parentPageBeanMap.put("fileName", parentPageDir);
                parentPageBeanMap.put("isSubPage", false);
            
                Page parentPageBean = new Page();

                HashMap pageBeanMap = new HashMap<>();
                pageBeanMap.put("name", subPageName);
                pageBeanMap.put("fileName", fileName);
                pageBeanMap.put("isSubPage", true);
                pageBeanMap.put("data", data);
            
                Page pageBean = new Page();
                try {
                    BeanUtils.populate(pageBean, pageBeanMap);
                    BeanUtils.populate(parentPageBean, parentPageBeanMap);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (destPath.equalsIgnoreCase("editpage")){
                    pageBean.setSearchTags(jsonFileHandler.getSubPageTags(section, parentPageName, subPageName));
                }
                request.setAttribute("page", pageBean);
                request.setAttribute("parentPage", parentPageBean);
            
            } else {
            sectionDirName = section.replace(" ", "_").toLowerCase();
            if (isSubPageRequest(request)){
                fileName = parentPageDir;
                pageName = parentPageName;
            }
            String data = Files.lines(Paths.get(PAGES_DIR + sectionDirName + "/" + fileName + ".md"))
            .collect(Collectors.joining(System.lineSeparator()));
            
            //Files.readString(Path.of(PAGES_DIR + sectionDirName + "/" + fileName + ".md"));
            request.setAttribute("section",section);

            HashMap pageBeanMap = new HashMap<>();
            pageBeanMap.put("name", pageName);
            pageBeanMap.put("fileName", fileName);
            pageBeanMap.put("data", data);
            
            Page pageBean = new Page();
            try {
                BeanUtils.populate(pageBean, pageBeanMap);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (destPath.equalsIgnoreCase("editpage")){
            pageBean.setSearchTags(jsonFileHandler.getTags(section, pageName));
            }
            request.setAttribute("page", pageBean);
            
            }
        }

        request.setAttribute("jsonContentPath", CONTENT_JSON_DIR);

        if (destPath.equalsIgnoreCase("index")) {
            destPath = "indexServlet";
        } else {
            destPath = destPath + ".jsp";
        }
        RequestDispatcher rd = request.getRequestDispatcher(destPath);
        rd.forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isDeletePageRequest(request) || isDeleteSubPageRequest(request)) {
            deletePage(request);
        } else if (request.getParameter("saveNewPage") != null) {
            createNewFile(request);
        } else {
            writeToExistingFile(request);
        }
        doGet(request, response);
	}

    protected void deletePage(HttpServletRequest request) {
        String fileName = request.getParameter("filename");
        String section = request.getParameter("section");
        String pageName = request.getParameter("pageName");
        if (isSubPageRequest(request)) {
            String parentPageName = request.getParameter("parentPageName");
            String parentPageDir = request.getParameter("parentPageDir");
            sectionDirName = section.replace(" ", "_").toLowerCase();
            deleteFile(PAGES_DIR + sectionDirName + "/" + parentPageDir + "/" + fileName + ".md");
            deleteDirIfEmpty(PAGES_DIR + sectionDirName + "/" + parentPageDir);
            jsonFileHandler.deleteSubPageFromContentJson(section, parentPageName, pageName);
        } else {
            sectionDirName = section.replace(" ", "_").toLowerCase();
            deleteFile(PAGES_DIR + sectionDirName + "/" + fileName + ".md");
            deleteDirIfEmpty(PAGES_DIR + sectionDirName);
            jsonFileHandler.deletePageFromContentJson(section, pageName);
        }
    
    }

    private void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    private void deleteDirIfEmpty(String path){
        File dir = new File(path);
        File[] files = dir.listFiles();

    // if the directory contains any file
        if(files.length == 0) {
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private boolean isCreateNewPageRequest(HttpServletRequest request){
       return request.getParameter("saveNewPage") != null;
    }

    private boolean isDeletePageRequest(HttpServletRequest request){
        return request.getParameter("deletePage") != null;
    }

    private boolean isDeleteSubPageRequest(HttpServletRequest request) {
        return request.getParameter("deleteSubPage") != null;
    }

    private boolean isSubPageRequest(HttpServletRequest request) {
        return request.getParameter("parentPageDir") != null;
    }

    private boolean isNewSectionInputEntered(HttpServletRequest request) {
        return !(request.getParameter("inputSectionName").isEmpty());
    }

    private String getSection(HttpServletRequest request){
        if (!isSubPageRequest(request)) {
            if (isNewSectionInputEntered(request)){
                return request.getParameter("inputSectionName");
            } 
        }
            return request.getParameter("section");
    }

    protected void createNewFile(HttpServletRequest request){
        String section;
        String[] tags;
        //Retrieving section
        section = getSection(request);

        if (request.getParameter("tag") != null){
            tags = request.getParameterValues("tag");
        } else {
            tags = new String[]{};
        }
        //Retrieving name of page
        String pageName = request.getParameter("pageName");
        pageFileName = pageName.replace(" ", "_").toLowerCase();
        sectionDirName = section.replace(" ", "_").toLowerCase();

        if (isSubPageRequest(request)) {
            String parentPageDir = request.getParameter("parentPageDir");
            String parentPageName = request.getParameter("parentPageName");
            String dirPath = PAGES_DIR + sectionDirName + "/" + parentPageDir;
            new File(dirPath).mkdirs();
            try {
                new File(dirPath + "/" + pageFileName + ".md").createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            writeToFile(dirPath + "/" + pageFileName + ".md", "#" + pageName);    
            log(section);
            log(parentPageName);
            log(pageName);
            log(pageFileName);
            jsonFileHandler.writeNewSubPageToContentJson(section, parentPageName, pageName, pageFileName, tags);
        } else {
            String dirPath = PAGES_DIR + sectionDirName;
            new File(dirPath).mkdirs();
            try {
                new File(dirPath + "/" + pageFileName + ".md").createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            writeToFile(dirPath + "/" + pageFileName + ".md", "#" + pageName);    
            jsonFileHandler.writeNewPageToContentJson(section, pageName, pageFileName, tags);
        }
    }

    protected void writeToExistingFile(HttpServletRequest request){
        String fileName = request.getParameter("filename");
        String section = request.getParameter("section");
        String mdData = request.getParameter("textarea");
        String[] tags = request.getParameterValues("tag");
        String pageName = request.getParameter("pageName");
        String sectionDirName = section.replace(" ", "_").toLowerCase();

        if (isSubPageRequest(request)){
            String parentPageName = request.getParameter("parentPageName");
            String parentPageDir = request.getParameter("parentPageDir");
            writeToFile(PAGES_DIR + sectionDirName + "/" + parentPageDir + "/" + fileName + ".md", mdData);  
            jsonFileHandler.updateSubPageInJsonContent(section, parentPageName, pageName, pageName, tags);
        } else {
            log("Trying to writ eto " + PAGES_DIR + section + "/" + fileName + ".md");
            writeToFile(PAGES_DIR + sectionDirName + "/" + fileName + ".md", mdData);  
            jsonFileHandler.updatePageInJsonContent(section, pageName, pageName, tags);
        }
               
        

        
    }

    protected void writeToFile(String path, String data){
        try {
            OutputStreamWriter writer 
            = new OutputStreamWriter(new FileOutputStream(path, false), StandardCharsets.UTF_8); 
            BufferedWriter bfWriter = new BufferedWriter(writer);
            bfWriter.write(data);
            bfWriter.flush();
            bfWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}