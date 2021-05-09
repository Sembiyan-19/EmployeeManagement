<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Deleted projects</title>
</head>
<body>
    <table border="1px" height=40% width=60% align="center">
        <tr>
		  <th>Id</th>
		  <th>Name</th>
		  <th></th>
		</tr>
		<c:forEach var="project" items="${projects}">
		  <tr>
			<td><c:out value="${project.id}" /></td>
			<td><c:out value="${project.name}" /></td>
			<td>
			  <a href="project?action=restoreProject&id=<c:out value='${project.id}' />">
			    <button>Restore</button>
			  </a>
			</td>
		  </tr>
		</c:forEach>
    </table>
    <br><br><br>
    <a href="project?action=showAllProjects"><button>Back</button></a>
</body>
</html>