<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.projectManagement.model.Project" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Project</title>
</head>
<body>
    <a href = "index.jsp"><button>Home</button></a><br><br><br>
	<% 
	    Project project = (Project)request.getAttribute("obj"); 
	    if (null != project) {
	%>
	<table border="1px" width="50%">
	    <tr>
	        <td>Id</td>
	        <td><%= project.getId() %></td>
	    </tr>
	    <tr>
	        <td>Name</td>
	        <td><%= project.getName() %></td>
	    </tr>
	    <tr>
	        <td>Manager</td>
	        <td><%= project.getManager() %></td>
	    </tr>
	    <tr>
	        <td>Department</td>
	        <td><%= project.getDepartment() %></td>
	    </tr>
	</table>
	<br><br><br>
	<a href = "project?action=delete&id=<%= project.getId() %>"><button>Delete</button></a>
	<a href = "project?action=edit&id=<%= project.getId() %>"><button>Edit</button></a>
    <% } %>
    <br><br><br>
    <a href="project?action=showAll"><button>Back</button></a>
</body>
</html>