<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,com.collabera.dao.*,com.collabera.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Employee</title>
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
String empId=request.getParameter("id");
String firstName=request.getParameter("firstName");
String lastName=request.getParameter("lastName");
Employee emp = new Employee(Integer.parseInt(empId), firstName, lastName);
emp = EmployeeDao.update(emp);
if(emp!=null){
	out.print("<h3>Employee : "
			+ emp.getId()
			+ " " + emp.getFirstName()
			+ " " + emp.getLastName()
			+ " => " + firstName
			+ " " + lastName
			+ " - Updated!</h3>");
}
%>
<hr>
<a href="view.jsp?pageNo=1">Back to Employee List</a>
</body>
</html>