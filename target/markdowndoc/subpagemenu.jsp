<%@ page import="java.io.FileInputStream" %>
<%@ page import="javax.json.JsonReader" %>
<%@ page import="javax.json.Json" %>
<%@ page import="javax.json.JsonObject" %>
<%@ page import="javax.json.JsonArray" %>
<%@ page import="javax.json.stream.JsonParser" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pojos.Page" %>
<%@ page import="pojos.Section" %>
<%@ page import="beans.ContentJsonFileHandler"%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<div class="sidenav-right">
<h4 class="subpages-header"><%=((Page)session.getAttribute("parentPage")).getName()%></h4>
<div id="menu-container-right">
       <%for (Object subPageObject : (List)session.getAttribute("subPages")) {
        Page subPage = (Page)subPageObject;%>
            <div class="<%=subPage.getFileName()%>-menu-page menu-subpage">
                <a type="button" class="subpage-menu-button" id="<%=subPage.getFileName().replace("_","-")%>-menu-page" href="<%=request.getContextPath()%>/fileHandlerServlet?section=<%=request.getAttribute("section").toString()%>&filename=<%=subPage.getFileName()%>&destPath=page&parentPageDir=<%=((Page)session.getAttribute("parentPage")).getFileName()%>&parentPageName=<%=((Page)session.getAttribute("parentPage")).getName()%>&pageName=<%=subPage.getName()%>"><span class="subpage-menu-button-text"><%=subPage.getName()%></span></a>
            </div>
        <%}%>
</div>
    <div id="bottom-right"><button id="openNewSubPageForm" class="new-page-button"><i class="fa fa-plus fa-xs plus-icon" aria-hidden="true"></i>New subpage</button></div>
</div>
<jsp:include page="dialog.jsp" />
    <script>
	$("document").ready(function () {
            //Handle menu
       
            });
		</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/collapsible-1.2.0/jquery.collapsible.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dialog.js"></script>