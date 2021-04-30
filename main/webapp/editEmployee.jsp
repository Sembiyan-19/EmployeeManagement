<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.projectManagement.model.Project" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Edit Employee</title>
</head>
<body>
	<c:if test="${obj != null}">
		<form action="employee?action=update" method="post">
			Employee id: <input type = "number" name = "id" value = "${obj.id}" required readonly><br><br>
			Employee name: <input type = "text" name = "name" value = "${obj.name}" required><br><br>
        	Salary: <input type = "text" name = "salary" value = "${obj.salary}" required><br><br>
        	Mobile number: <input type = "text" name = "mobileNumber" value = "${obj.mobileNumber}" required><br><br>
        	Date of birth: <input type = "date" name = "dateOfBirth" value = "${obj.dateOfBirth}" required><br><br>
        	<input type = "submit">
        </form>
	</c:if>
	<c:if test="${obj == null}">
		<form action="employee?action=insert" method = "post">
	    	Employee id: <input type = "number" name = "id" required><br><br>
			Employee name: <input type = "text" name = "name" required><br><br>
        	Salary: <input type = "text" name = "salary" required><br><br>
        	Mobile number: <input type = "text" name = "mobileNumber" required><br><br>
        	Date of birth: <input type = "date" name = "dateOfBirth" required><br><br>
        	permanent address: <input type = "text" name = "doorNumber" placeholder = "door number" required>
                 <input type = "text" name = "street"  placeholder = "street" required>
                 <input type = "text" name = "city"  placeholder = "city" required>
                 <input type = "text" name = "pincode"  placeholder = "pincode" required>
                 <input type = "text" name = "state"  placeholder = "state" required>
                 <input type = "text" name = "country"  placeholder = "country"><br><br>
        	Optional address: <input type = "text" name = "doorNumberO" placeholder = "door number" required>
                 <input type = "text" name = "streetO"  placeholder = "street" required>
                 <input type = "text" name = "cityO"  placeholder = "city" required>
                 <input type = "text" name = "pincodeO"  placeholder = "pincode" required>
                 <input type = "text" name = "stateO"  placeholder = "state" required>
                 <input type = "text" name = "countryO"  placeholder = "country" required><br><br>
        <input type = "submit"> 
	    </form>
		</c:if>
		<br><br><br>
		<c:if test="${obj != null}">
		<a href="employee?action=view&id=${obj.id}"><button>Back</button></a>
    </c:if>
    <c:if test="${obj == null}">
        <a href="employee?action=showAll"><button>Back</button></a>
    </c:if>
</body>
</html>
