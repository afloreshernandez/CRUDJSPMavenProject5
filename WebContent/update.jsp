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
String sId=request.getParameter("id");
int id=Integer.parseInt(sId);
Employee emp = new Employee(-1, "", "");
String readonly="";
if(id>0){
	readonly="readonly";
	emp = EmployeeDao.getEmployee(id);
}
%>

<h2>Update Employee Record: </h2>
	<form action="doUpdate.jsp" method="post">
		<label>Id:</label>
			<input type="text" name="id" <%=readonly %> value="<%=emp.getId() %>"><br><br>
		<label>First Name:</label>
			<input type="text" name="firstName" value="<%=emp.getFirstName() %>"><br>
		<label>Last Name:</label>
			<input type="text" name="lastName" value="<%=emp.getLastName() %>"><br>
		<input type="submit" value="Save">
	</form>
<br>
<a href="view.jsp?pageNo=1">Back to Employee List</a>
</body>
</html>