<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.employeeManagement.model.Employee" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View employee</title>
</head>
<body>
    
	<% 
	    Employee employee = (Employee)request.getAttribute("obj"); 
	    if (null != employee) {
	%>
	<table border="1px" width="50%">
	    <tr>
	        <td>Id</td>
	        <td><%= employee.getId() %></td>
	    </tr>
	    <tr>
	        <td>Name</td>
	        <td><%= employee.getName() %></td>
	    </tr>
	    <tr>
	        <td>Salary</td>
	        <td><%= employee.getSalary() %></td>
	    </tr>
	    <tr>
	        <td>mobile Number</td>
	        <td><%= employee.getMobileNumber() %></td>
	    </tr>
	    <tr>
	        <td>Date of Birth</td>
	        <td><%= employee.getDateOfBirth() %></td>
	    </tr>
	</table>
	<br><br><br>
	<a href = "employee?action=delete&id=<%= employee.getId() %>"><button>Delete</button></a>
	<a href = "employee?action=edit&id=<%= employee.getId() %>"><button>Edit</button></a>
    <% } %>
    <br><br><br>
    <a href="employee?action=showAll"><button>Back</button></a>
</body>
</html>