<%@ page language = "java" contentType = "text/html; charset=ISO-8859-1"
    pageEncoding = "ISO-8859-1"%>
<%@ page import = "com.ideas2it.projectManagement.model.Project" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<html>
<head>
    <title>Project Home</title>
</head>
<body>
	<a href="project?action=new">Add New Project</a>
	<br>
	<br>
	<div align = "right">
		<form action = "project?action=view" method = "post">
			View a project: <input type = "text" name = id>
							<input type = "submit">
		</form>
	</div>
	<br>
	<br>
	<table border = "1px" width = 50% align = "center">
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Department</th>
				<th colspan = "3">Actions</th>
			</tr>
		</thead>
		<tbody>
		<!--   for (Todo todo: todos) {  -->
			<c:forEach var = "project" items = "${obj}">
				<tr>
					<td><c:out value = "${project.id}" /></td>
					<td><c:out value = "${project.name}" /></td>
					<td><c:out value = "${project.department}" /></td>
					<td><a href = "project?action=view&id=<c:out value='${project.id}' />">View</a></td>
					<td><a href = "project?action=edit&id=<c:out value='${project.id}' />">Edit</a></td>
					<td><a href = "project?action=delete&id=<c:out value='${project.id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		<!-- } -->
		</tbody>
	</table>
</body>
</html>
