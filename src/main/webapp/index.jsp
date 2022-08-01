<%@ page import="org.commonmark.parser.Parser" %>
<%@ page import="org.commonmark.node.Node" %>
<%@ page import="org.commonmark.renderer.html.HtmlRenderer" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Office Assistent</title>
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/showdown/2.1.0/showdown.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/dialog.js"></script>
</head>
<body>
<%session.setAttribute("jsonContentPath", request.getAttribute("jsonContentPath").toString());
session.setAttribute("companyName", request.getAttribute("companyName").toString());%>
<div class="content">
    <jsp:include page="top.jsp" />
    <div id="page" class="fill">
     <jsp:include page="menu.jsp" />
        
        <div id="main"></div>
           

</div>

<jsp:include page="dialog.jsp" />
</body>
</html>