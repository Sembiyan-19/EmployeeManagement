<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
   <head>
    <title>Employee Home</title>
  </head>
  <body>
    <a href="employee?action=index"><button>Home</button></a>
    <br><br><br>
	<a href="employee?action=createEmployee"><button>Add New employee</button></a>
	<br><br>
	<div align="right">
	  <form action="employee?action=showEmployee" method="post">
		View a Employee: <input type="text" name="id">
		<input type="submit">
	  </form><br>
	<br>
	</div>
	<br><br>
	<table border="1px" width=70% align="center">
		<tr>
		  <th>Id</th>
		  <th>Name</th>
		  <th>Salary</th>
		  <th>Mobile Number</th>
		  <th>Date Of Birth</th>
		  <th colspan="2">Actions</th>
		</tr>
	  <c:forEach var="employee" items="${employees}">
		  <tr>
			<td><c:out value="${employee.id}" /></td>
			<td><c:out value="${employee.name}" /></td>
			<td><c:out value="${employee.salary}" /></td>
			<td><c:out value="${employee.mobileNumber}" /></td>
			<td><c:out value="${employee.dateOfBirth}" /></td>
			<td>
			  <a href="employee?action=showEmployee&id=<c:out value='${employee.id}' />">
			    View
			  </a>
			</td>
			<td>
			  <a href="employee?action=deleteEmployee&id=<c:out value='${employee.id}' />">
			    Delete
			  </a>
			</td>
		  </tr>
		</c:forEach>
	</table>
	<br><br><br>
	<div align="center"><a href="employee?action=viewDeletedEmployees"><button>Restore employee</button></a></div>
	<a href="employee?action=index"><button>Back</button></a>	
  </body>
</html>
