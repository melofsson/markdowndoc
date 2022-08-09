<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<jsp:include page="/top.jsp" />
<%! String getName(String firstname) {
if (firstname.equalsIgnoreCase("Mikael")) {
    return "Elofsson";
} else if(firstname.equalsIgnoreCase("Gerd")) {
    return "Lindrud";
    }
return null;
}
%>
<%
String name = request.getAttribute("keyword");
%>
<p>Du sökte på <%=name%></p>
</body>
</html>