<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.employeeManagement.model.Employee" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
<a href="index.jsp"><button>Home</button></a>
    <br><br><br>
				<a href="employee?action=new"><button>Add New employee</button></a>
			    <br>
	<br>
	<div align = "right">
		<form action = "employee?action=view" method = "post">
			View a Employee: <input type = "text" name = id>
							<input type = "submit">
		</form>
	</div>
	<br>
	<br>
			<table border = "1px" width = 70% align = "center">
				<thead>
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Salary</th>
						<th>Mobile Number</th>
						<th>Date Of Birth</th>
						<th colspan = "2">Actions</th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="employee" items="${obj}">

						<tr>
							<td><c:out value="${employee.id}" /></td>
							<td><c:out value="${employee.name}" /></td>
							<td><c:out value="${employee.salary}" /></td>
							<td><c:out value="${employee.mobileNumber}" /></td>
							<td><c:out value="${employee.dateOfBirth}" /></td>
							<td><a href = "employee?action=view&id=<c:out value='${employee.id}' />">View</a></td>
					<td><a href = "employee?action=delete&id=<c:out value='${employee.id}' />">Delete</a></td>
						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table><br><br><br>
	<a href="index.jsp"><button>Back</button></a>
		
</body>
</html>
