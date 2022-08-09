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
<div class="sidenav">
<div id="menu-container" class="collapse-container">
       <jsp:useBean id="pageHandler" class="beans.ContentJsonFileHandler"/>  
       <%pageHandler.setJsonContentPath(session.getAttribute("jsonContentPath").toString());
       List<Section> sections = pageHandler.getSections();
         for (Section section : sections) {
             %><%String sectionClass = section.name.toLowerCase().replace(" ", "-");%>
             <div class="<%=sectionClass%>-header section-header"><i class="fa fa-caret-right section-arrow"></i><span class="section-title"><%=section.name%></span></div>
                <div class="<%=sectionClass%>-pages-div pages-div pages-hidden">
                    <% for (Page singlePage : section.pages) { %>
                        <div class="<%=singlePage.getFileName()%>-menu-page menu-page">
                            <a class="page-menu-button" type="button" id="<%=singlePage.getFileName().replace("_","-")%>-menu-page" href="<%=request.getContextPath()%>/fileHandlerServlet?section=<%=section.name%>&filename=<%=singlePage.getFileName()%>&destPath=page&pageName=<%=singlePage.getName()%>"><span class="page-menu-button-text"><%=singlePage.getName()%></span></a>
                        </div>
                        
                    <%}%>
                </div>
        <%}%>
</div>
    <div id="bottom"><button id="openNewPageForm" class="new-page-button"><i class="fa fa-plus fa-xs plus-icon" aria-hidden="true"></i>New page</button></div>
</div>

    <script>
    $(function() {
        $('#menu-container').collapsible({
        animate: false
        });
    });
	$("document").ready(function () {
            //Handle menu
        <%if (request.getAttribute("section") != null) {
            String section = request.getParameter("section").toLowerCase().replace("_", "-").replace(" ", "-");%>
            $(".<%=section%>-pages-div").css("display", "block");
            $(".<%=section%>-pages-div").removeClass("pages-hidden");
            <%if (request.getAttribute("parentPage") != null) {%>
                $("#${parentPage.getFileName().replace("_","-")}-menu-page").css("text-decoration", "underline");
            <%}%>
            $("#${page.getFileName().replace("_","-")}-menu-page").css("text-decoration", "underline");
        <%}%>
            });
		</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/collapsible-1.2.0/jquery.collapsible.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dialog.js"></script>