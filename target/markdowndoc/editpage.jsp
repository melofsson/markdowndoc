<%@ page import="org.commonmark.parser.Parser" %>
<%@ page import="org.commonmark.node.Node" %>
<%@ page import="org.commonmark.renderer.html.HtmlRenderer" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="pojos.Page" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/showdown-table.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/collapsible-1.2.0/jquery.collapsible.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/resizableborder.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/editmd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dialog.js"></script>
</head>
<body>
<div class="content">
    <jsp:include page="top.jsp" />
    <div id="page" class="fill">
        <jsp:include page="menu.jsp" />
                <div id="main" class="main-editmode">
                <div id="main-topbar" class="edit-enabled topbar-editpage border-bottom">
                <div id=topbar-left>
                <%if (((Page)request.getAttribute("page")).getIsSubPage() == true) {%>
                    <a href="<%=request.getContextPath()%>/fileHandlerServlet?section=${section}&filename=${page.getFileName()}&destPath=page&parentPageDir=${parentPage.getFileName()}&parentPageName=${parentPage.getName()}&pageName=${page.getName()}" class="clean-btn" id="back"><i class="fa fa-chevron-left" aria-hidden="true"></i></a>
                <%} else {%>
                    <a href="<%=request.getContextPath()%>/fileHandlerServlet?section=${section}&&filename=${page.getFileName()}&&destPath=page&&pageName=${page.getName()}" class="clean-btn" id="back"><i class="fa fa-chevron-left" aria-hidden="true"></i></a>
                <%}%>
                <select id="header" onchange="updateHeader()">
                    <option value="" disabled selected>Font size</option>
                    <option value="header1">Header 1</option>
                    <option value="header2">Header 2</option>
                    <option value="header3">Header 3</option>
                    <option value="header4">Header 4</option>
                    <option value="header5">Header 5</option>
                    <option value="header6">Header 6</option>
                </select>
                <button class="edit-btn inactive-edit-btn" id="boldText"><i class="fa fa-bold" aria-hidden="true"></i></button>
                <button class="edit-btn inactive-edit-btn" id="italicText"><i class="fa fa-italic" aria-hidden="true"></i></button>
                <button class="edit-btn inactive-edit-btn" id="linkBtn"><i class="fa fa-link" aria-hidden="true"></i></button>
                <button class="edit-btn inactive-edit-btn" id="tableBtn"><i class="fa fa-table" aria-hidden="true"></i></button>
                
                <button class="edit-btn inactive-edit-btn" id="codeText"><i class="fa fa-code" aria-hidden="true"></i></button>
                <button class="edit-btn inactive-edit-btn" id="img"><i class="fa fa-file-image-o" aria-hidden="true"></i></button>
                <button class="edit-btn inactive-edit-btn" type="submit" id="video"><i class="fa fa-video-camera" aria-hidden="true"></i></button>
                <input id="file-input" type="file" name="name" style="display: none;" />
                <input id="filedir" type="hidden" name="filedir" value="${section}"/>

                <form name="mdForm" id="mdForm" action="fileHandlerServlet" method="POST" accept-charset="utf-8">
                <button id="save-btn" class="file-btn" type="Submit" value="SAVE"><i class="fa fa-floppy-o fa-lg" aria-hidden="true"></i></button>
                <input type="hidden" name="filename" id="filename" value="${page.getFileName()}"/>
                <input type="hidden" name="destPath" id="destPath" value="page" />
                <input type="hidden" name="section" id="section" value="${section}" />
                <input type="hidden" name="pageName" id="pageName" value="${page.getName()}" />
                <%if (((Page)request.getAttribute("page")).getIsSubPage() == true) {%>
                    <input type="hidden" name="parentPageName" id="pageName" value="${parentPage.getName()}" />
                    <input type="hidden" name="parentPageDir" id="pageName" value="${parentPage.getFileName()}" />
                    <%}%>
                </form>
                </div>
                
                <div id="main-bottombar">
                <div id=bottombar-left>
                <code id="tag-title">TAGS:</code>
                <div class="tags">
                <c:forEach var="tag" items="${page.getSearchTags()}" >
                    <span class="tag">
                        <input form="mdForm" type="hidden" name="tag" value="${tag}"/><code class="tag-block">${tag}</code>
                        <span class="tag-cross hidden">x</span>
                    </span>
                </c:forEach>
                </div>
                <button id="add-tag-btn"><i class="fa fa-plus fa-sm" aria-hidden="true"></i></button>
                </div>
                
                </div></div>

                    <textarea form="mdForm" name="textarea" id="mdcontent">
                </textarea>
                
                <div id="preview-content">
                </div>
            </div>

    </div>

</div>
<jsp:include page="dialog.jsp" />
<script>loadMdEditContent(`${page.getData()}`);</script>
<!--<form action="welcome.jsp">
    <video controls class="video" preload="metadata" poster="video/pudel.jpg">
        <source src="video/cloning_repo.mov" type="video/mp4"></source>
    </video>
</form>-->

</body>
</html>