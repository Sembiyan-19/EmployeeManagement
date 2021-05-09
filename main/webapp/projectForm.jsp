<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>Edit Project</title>
  </head>
  <body>
	<form action="project?action=${operation}" method = "post">
	  Project id: <input type = "number" name = "id" value = "${project.id}" required <c:if test="${project != null}"> <c:out value="${'readonly'}"/> </c:if>><br><br>
	  Project name: <input type = "text" name = "name" value = "${project.name}" required><br><br>
	  Project manager: <input type = "text" name = "manager" value = "${project.manager}" required><br><br>
	  Department: <input type = "text" name = "department" value = "${project.department}" required><br><br>
	  <input type = "submit">
	</form>
	<br><br><br>
	<c:if test="${project != null}">
	  <a href="project?action=showProject&id=${project.id}"><button>Back</button></a>
    </c:if>
    <c:if test="${project == null}">
      <a href="project?action=showAllProjects"><button>Back</button></a>
    </c:if>
  </body>
</html>
