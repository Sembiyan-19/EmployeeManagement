<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <h1>Enter the employee details</h1>
    <form action="employee?action=update&id=<%= request.getParameter("id") %>" method = "post">
        Employee id : <%= request.getParameter("id") %><br><br>
        Employee name: <input type = "text" name = "name"><br><br>
        Employee salary: <input type = "text" name = "salary"><br><br>
        Employee mobile number: <input type = "text" name = "mobileNumber"><br><br>
        Employee date of birth: <input type = "date" name = "dateOfBirth"><br><br>
        <input type = "submit"> 
    </form>
    <br>
</body>
</html>