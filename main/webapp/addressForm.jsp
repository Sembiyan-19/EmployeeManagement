<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>Edit Address</title>
</head>
<body>
  <form action="employee?action=${operation}&id=${id}&option=${option}" method="post">
    Door number: <input type = "text" name = "doorNumber" value = "${address.doorNumber}" required><br><br>
    Street: <input type = "text" name = "street"  value = "${address.street}" required><br><br>
    City: <input type = "text" name = "city"  value = "${address.city}" required><br><br>
    Pincode: <input type = "text" name = "pincode"  value = "${address.pincode}" required><br><br>
    State: <input type = "text" name = "state"  value = "${address.state}" required><br><br>
    Country: <input type = "text" name = "country"  value = "${address.country}"><br><br>
    <input type = "submit">
  </form>
  <br><br>
  <a href = "employee?action=showEmployee&id=${id}"><button>Cancel</button></a>
  </body>
</html>