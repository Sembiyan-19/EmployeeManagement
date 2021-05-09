<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>Project Home</title>
  </head>
  <body>
    <a href="project?action=index"><button>Home</button></a>
    <br><br><br>
	<a href="project?action=createProject"><button>Add New Project</button></a>
	<br><br>
	<div align="right">
	  <form action="project?action=showProject" method="post">
		View a project: <input type="number" name=id required>
		<input type="submit">
	  </form>
	<br>
	<br>
	</div>
	<br>
	<br>
	<table border="1px" height=40% width=60% align="center">
		<tr>
		  <th>Id</th>
		  <th>Name</th>
		  <th>Department</th>
		  <th colspan="2">Actions</th>
		</tr>
		<c:forEach var="project" items="${projects}">
		  <tr>
			<td><c:out value="${project.id}" /></td>
			<td><c:out value="${project.name}" /></td>
			<td><c:out value="${project.department}" /></td>
			<td>
			  <a href="project?action=showProject&id=<c:out value='${project.id}' />">
			    View
			  </a>
			</td>
			<td>
			  <a href="project?action=deleteProject&id=<c:out value='${project.id}' />">
			    Delete
			  </a>
			</td>
		  </tr>
		</c:forEach>
	</table>
	<br><br><br>
	<div align="center"><a href="project?action=showDeletedProjects"><button>Restore project</button></a></div>
	<a href="project?action=index"><button>Back</button></a>
  </body>
</html>
