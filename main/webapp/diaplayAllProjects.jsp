<%@ page import = "com.ideas2it.projectManagement.model.Project" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Project</title>
</head>
<body>

    <%
        //List<String> projects = (List<String>)request.getAttribute("obj");
        //for (String a: projects) {
            //out.println(a);
        //}
    %>
    
         <table border=j"1">
              <tr>
                  <th>Column 1</th>
              </tr>
              <c:forEach var="data" items="${obj}">
                  <tr>
                      <td><c:out value="${data}" /></td>
                      
                  </tr>
              </c:forEach>
          </table>
    
</body>
</html>