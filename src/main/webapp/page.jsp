<%@ page import="org.commonmark.parser.Parser" %>
<%@ page import="org.commonmark.node.Node" %>
<%@ page import="org.commonmark.renderer.html.HtmlRenderer" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pojos.Page" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Office Assistent</title>
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/showdown/2.1.0/showdown.min.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/previewmd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dialog.js"></script>
</head>
<body>
<div class="content">
    <jsp:include page="top.jsp" />
    <div id="page" class="fill">
        <jsp:include page="menu.jsp" />
        <jsp:useBean id="pageHandler" class="beans.ContentJsonFileHandler"/>
        <%pageHandler.setJsonContentPath(session.getAttribute("jsonContentPath").toString());
        List<Page> subPages;
        Page parentPage; 
        if (((Page)request.getAttribute("page")).getIsSubPage() == true) {
            parentPage = (Page)request.getAttribute("parentPage");
        } else {
            parentPage = (Page)request.getAttribute("page");
        }
        session.setAttribute("section", request.getAttribute("section").toString());
        subPages = pageHandler.getSubPages(request.getAttribute("section").toString(), parentPage.getName());
        session.setAttribute("parentPage",parentPage);  
        session.setAttribute("subPages",subPages);  
        %>
        <jsp:include page="subpagemenu.jsp" />
        <div id="main" class="main-pagemode">
        <div id="innermain">
        <div id="edit-buttons">
                   <%if (((Page)request.getAttribute("page")).getIsSubPage() == true) {%>
                    <a type="button" class="file-btn" href="<%=request.getContextPath()%>/fileHandlerServlet?section=${section}&&filename=${page.getFileName()}&&destPath=editpage&parentPageName=${parentPage.getName()}&parentPageDir=${parentPage.getFileName()}&pageName=${page.getName()}"><i class="fa fa-pencil-square-o"></i></a>
                   <%} else {%>
                    <a type="button" class="file-btn" href="<%=request.getContextPath()%>/fileHandlerServlet?section=${section}&&filename=${page.getFileName()}&&destPath=editpage&&pageName=${page.getName()}"><i class="fa fa-pencil-square-o"></i></a>
                   <%}%>
                        
                        <a type="button" onclick="openSettingsDialog()" id="settings-btn" class="file-btn"><i class="fa fa-ellipsis-v"></i></a>
                        <div id="settingsDropDown" class="dropdown-content">
                        
                        <form name="deletePageForm" action="fileHandlerServlet" method="POST" accept-charset="utf-8">
                            
                            <input type="hidden" name="section" value="${section}"/>
                            <input type="hidden" name="filename" value="${page.getFileName()}"/>
                            <input type="hidden" name="pageName" value="${page.getName()}"/>
                            <%if (((Page)request.getAttribute("page")).getIsSubPage() == true) {%>
                                <input type="hidden" name="parentPageName" value="${parentPage.getName()}"/>
                                <input type="hidden" name="parentPageDir" value="${parentPage.getFileName()}"/>
                                <input type="hidden" name="destPath" value="page"/>
                                <input type="hidden" name="deleteSubPage" id="deleteSubPage" value="true"/>
                            <%} else {%>
                                <input type="hidden" name="destPath" value="index"/>
                                <input type="hidden" name="deletePage" id="deletePage" value="true"/>
                            <%}%>
                            
                            <input type="Submit" href="" value="Delete page"/>
                        </form>
                            
                        </div>
                   </div>
                   </div>
            <div id="main-topbar" class="topbar-page">
                <div id=topbar-left>
                <%String header;
                if (((Page)request.getAttribute("page")).getIsSubPage() == true) {%>
                <p id="page-header">${section} > ${parentPage.getName()} > ${page.getName()}</p>
                <%} else {%>
                <p id="page-header">${section} > ${page.getName()}</p>
                <%}%>
        
                   
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="dialog.jsp" />
<script>convertToHtml(`${page.getData()}`);</script>
<!--<form action="welcome.jsp">
    <video controls class="video" id="video" preload="metadata" poster="video/pudel.jpg">
        <source src="video/cloning_repo.mov" type="video/mp4"></source>
    </video>
</form>-->

</body>
</html>