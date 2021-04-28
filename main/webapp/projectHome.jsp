<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Home</title>
<style>
    h1 {
        text-align: center;
        font-size: 50px
    }
    
</style>
</head>
<body>
    <a href = "index.jsp"><button>Home</button></a>
    <h1>Project Management</h1>
    <h2><a href = "addProject.jsp">Add new project</a></h2>
    <h2><a href = "viewProject.jsp">View a project</a></h2>
    <h2><a href = "updateProject.jsp">update</a></h2>
    <h2><a href = "project?action=showAll">Show all projects</a></h2>
    <br>
    <a href="index.jsp"><button>Back</button></a>
</body>
</html>