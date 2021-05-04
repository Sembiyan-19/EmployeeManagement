<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error Page</title>
</head>
<body>
    <c:if test="${usedProjectId != null}">
    	<h2>Project Id already used. Please provide a different project Id</h2>
    	<br><br>
    	<a href="project?action=new"><button>Back</button></a>
    </c:if>
    <c:if test="${usedEmployeeId != null}">
    	<h2>Employee Id already used. Please provide a different employee Id</h2>
    	<br><br>
    	<a href="employee?action=new"><button>Back</button></a>
    </c:if>
    <c:if test="${invalidProjectId != null}">
    	<h2>No such project Id present. Please provide a different project Id</h2>
    	<br><br>
    	<a href="project?action=showAll"><button>Back</button></a>
    </c:if>
    <c:if test="${invalidEmployeeId != null}">
    	<h2>No such employee Id present. Please provide a different employee Id</h2>
    	<br><br>
    	<a href="employee?action=showAll"><button>Back</button></a>
    </c:if>
</body>
</html>