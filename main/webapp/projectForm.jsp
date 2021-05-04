<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.projectManagement.model.Project" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>Edit Project</title>
  </head>
  <body>
	<c:if test="${project != null}">
	  <form action="project?action=update" method="post">
		Employee id: <input type = "number" name = "id" value = "${project.id}" required readonly><br><br>
	</c:if>
	<c:if test="${project == null}">
	  <form action="project?action=insert" method = "post">
	    Employee id: <input type = "number" name = "id" value = "${project.id}" required><br><br>
	</c:if>
	  Project name: <input type = "text" name = "name" value = "${project.name}" required><br><br>
	  Project manager: <input type = "text" name = "manager" value = "${project.manager}" required><br><br>
	  Department: <input type = "text" name = "department" value = "${project.department}" required><br><br>
	  <input type = "submit">
	</form>
	<br><br><br>
	<c:if test="${project != null}">
	  <a href="project?action=view&id=${project.id}"><button>Back</button></a>
    </c:if>
    <c:if test="${project == null}">
      <a href="project?action=showAll"><button>Back</button></a>
    </c:if>
  </body>
</html>