<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="java.util.*,com.collabera.dao.*,com.collabera.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Employee</title>
<style>
			body{
	background-color:black;
	color: green;
	font-family: "Verdana", Verd, Sans-serif;
	
	}
	a{
		padding: 4px;
		margin: 0 10px;
		text-decoration: none;
		font-weight: bold;
		color: yellow;
		
	}
	a:hover{
		color: yellow;
	}
</style>
</head>
<body>

<%
int id = 0;
if(session != null){
	// it's a string so you have to convert to int and store in integer int
	id = Integer.parseInt(request.getParameter("id"));
	
	// HINT2: How to delete the employee with that id? EmployeeDao has a static method delete(), so...
			EmployeeDao.delete(id); //<----  Complete this line
					
			
}

%>

<h1>Employee deleted!</h1>
<hr>
<a href="view.jsp?pageNo=1">back to employees</a>

</body>
</html>