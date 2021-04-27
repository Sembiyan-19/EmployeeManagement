<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <h1>Enter the project details</h1>
    <form action="project?action=add" method = "post">
        Project id: <input type = "number" name = "id"><br><br>
        Project name: <input type = "text" name = "name"><br><br>
        Project manager: <input type = "text" name = "manager"><br><br>
        Department: <input type = "text" name = "department"><br><br>
        <input type = "submit"> 
    </form>
</body>
</html>