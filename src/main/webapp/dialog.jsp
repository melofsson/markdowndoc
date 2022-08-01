<%@ page import="pojos.Section" %>
<%@ page import="java.util.List" %>
<%@ page import="pojos.Page" %>
<%@ page import="beans.ContentJsonFileHandler" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<jsp:useBean id="pageHandler" class="beans.ContentJsonFileHandler"/>  
<%pageHandler.setJsonContentPath(session.getAttribute("jsonContentPath").toString());
List<Section> sections = pageHandler.getSections();%>
<div id="dialog" class="not-displayed">
<button id="close-dialog">X</button>
<div><h3 id="dialog-title">Add new page</h3></div>
    <form id="new-page-form" action="fileHandlerServlet" method="POST" accept-charset="utf-8">
    <div>
        <div><input id="inputSectionName" name="inputSectionName" type="text" placeholder="Section name" value=""></div>
        <input type="hidden" name="saveNewPage" id="saveNewPage" value="true"/>
        <select id="sections" name="section">
        <option value="" disabled selected>Select section</option>
        <%for (Section section : sections) {
             %><option value="<%=section.name.toLowerCase()%>"><%=section.name%></option>
            
    <%}%>
        </select><a href="" id="newSectionLink">Create new section</a></div>
    <div><input type="text" name="pageName" placeholder="Page name" value=""></div>
    <div><input type="Submit" value="Save"></div>
    </form>

    <%if (session.getAttribute("parentPage") != null){%>
     <form id="new-subpage-form" action="fileHandlerServlet" method="POST" accept-charset="utf-8">
    <div><p>To page <%=((Page)session.getAttribute("parentPage")).getName()%></p>
        <input type="hidden" name="section" id="section" value="<%=session.getAttribute("section").toString()%>"/>
        <input type="hidden" name="saveNewPage" id="saveNewPage" value="true"/>
        <input type="hidden" name="parentPageName" id="parentPageName" value="<%=((Page)session.getAttribute("parentPage")).getName()%>"/>
        <input type="hidden" name="parentPageDir" id="parentPageDir" value="<%=((Page)session.getAttribute("parentPage")).getFileName()%>"/>
    </div>
    <div><input type="text" name="pageName" placeholder="Page name" value=""></div>
    <div><input type="Submit" value="Save"></div>
    </form>
    <%}%>

    <div id="new-tag-form">
    <div><input id="new-tag-name" type="text" name="pageName" placeholder="Tag" value=""><button id="submit-new-tag">Add tag</button></div>
    </div>
    
    </div>
    <script>
    
        $("document").ready(function () {
            $("#submit-new-tag").click(function(){
                $(".tags").append("<span class='tag'>" + 
                        "<input form='mdForm' type='hidden' name='tag' value='" + $("#new-tag-name").val() + "'/>"+
                        "<code class='tag-block'>" + $("#new-tag-name").val() + "</code>" +
                        "<span class='tag-cross hidden'>x</span></span>");
                $("#new-tag-name").val("");
                 $('#close-dialog').trigger('click');     

                
            });

        });
		</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/collapsible-1.2.0/jquery.collapsible.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dialog.js"></script>