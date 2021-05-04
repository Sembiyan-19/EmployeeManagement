<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.ideas2it.projectManagement.model.Project" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <title>Project Home</title>
  </head>
  <body>
    <a href="project?action=index"><button>Home</button></a>
    <br><br><br>
	<a href="project?action=new"><button>Add New Project</button></a>
	<br><br>
	<div align="right">
	  <form action="project?action=view" method="post">
		View a project: <input type="text" name=id required>
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
			  <a href="project?action=view&id=<c:out value='${project.id}' />">
			    View
			  </a>
			</td>
			<td>
			  <a href="project?action=delete&id=<c:out value='${project.id}' />">
			    Delete
			  </a>
			</td>
		  </tr>
		</c:forEach>
	</table>
	<br><br><br>
	<div align="center"><a href="project?action=viewDeletedProjects"><button>Restore project</button></a></div>
	<a href="project?action=index"><button>Back</button></a>
  </body>
</html>
