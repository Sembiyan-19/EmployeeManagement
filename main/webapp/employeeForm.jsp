<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>Edit Employee</title>
</head>
<body>
	<form action="employee?action=${operation}" method="post">
	  Employee id: <input type = "number" name = "id" value = "${employee.id}" required <c:if test="${employee != null}"> <c:out value="${'readonly'}"/> </c:if>><br><br>
	  Employee name: <input type = "text" name = "name" value = "${employee.name}" required><br><br>
      Salary: <input type = "text" name = "salary" value = "${employee.salary}" required><br><br>
      Mobile number: <input type = "text" name = "mobileNumber" value = "${employee.mobileNumber}" required pattern="[6789][0-9]{9}"><br><br>
      Date of birth: <input type = "date" name = "dateOfBirth" value = "${employee.dateOfBirth}" required><br><br>
     <c:if test="${employee == null}">
      permanent address: <input type = "text" name = "doorNumber" placeholder = "door number" required>
        <input type = "text" name = "street"  placeholder = "street" required>
        <input type = "text" name = "city"  placeholder = "city" required>
        <input type = "text" name = "pincode"  placeholder = "pincode" required>
        <input type = "text" name = "state"  placeholder = "state" required>
        <input type = "text" name = "country"  placeholder = "country"><br><br>
      </c:if>
      <input type = "submit"> 
	</form>
  <br><br><br>
  <c:if test="${employee != null}">
	<a href="employee?action=showEmployee&id=${employee.id}"><button>Back</button></a>
  </c:if>
  <c:if test="${employee == null}">
    <a href="employee?action=showAllEmployees"><button>Back</button></a>
  </c:if>
</body>
</html>
