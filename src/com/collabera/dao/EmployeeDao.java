package com.collabera.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.collabera.model.Employee;

public class EmployeeDao {
	private static final Logger logger = Logger.getLogger(EmployeeDao.class.getName()); // log 4J named logger
	
	static Map<Integer, Employee> employeeMap = new TreeMap<Integer, Employee>(); // TreeMap to populate with employees
	
	private static HashMap<String, Employee> cache = new HashMap<String, Employee>(); // HashMap to hold a selection of employees
	
	// A method for establishing a connection to the database
	public static Connection Connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/week5", "root", "root");
		return con;
	}
	
	// Method to get list of employees and pass them to the TreeMap
	public static List<Employee> getList(int pageNo, int pageSize) throws SQLException{
		
		
		PreparedStatement pstmt = Connect().prepareStatement(
				"SELECT * FROM Employees");
		
		List<Employee> list = new ArrayList<Employee>();
		try {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Employee c = resultSetToEmployee(rs);
				list.add(c);
			}
		} catch (SQLException sqle) {
			logger.error("error executing: " + sqle);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();

				} catch (SQLException e) {
					/* ignore it */ }
			}

		}
		
		// Pass each employee found in the query list to the TreeMap employeeMap
		for(Employee emp : list) {
			employeeMap.put(emp.getId(),emp);;
		}
		
		return employeeMap.entrySet()
				.stream()
				.skip((pageNo-1)*pageSize)
				.limit(pageSize)
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
		
	}
	
	// Method to get a specific employee by id
	public static Employee getEmployee(int id) throws SQLException {		
		PreparedStatement statement = Connect().prepareStatement(
				"SELECT * FROM Employees WHERE id = ?");
		statement.setInt(1, id);
		try {
			statement.executeQuery();
		} catch (SQLException sqle) {
			logger.error("error executing select employee for id: " + id);
		} finally {
			statement.close();
		}
		
		return employeeMap.get(id);
	}
	
	// Insert/Add/Create a new employee to the database
	public static Employee insert(Employee emp) throws SQLException {
		PreparedStatement statement = Connect().prepareStatement(
				"INSERT INTO employees(firstName, lastName) VALUES(?,?)");
		
		statement.setString(1, emp.getFirstName());
		statement.setString(2, emp.getLastName());
		try {
			statement.executeUpdate();
		} catch (SQLException sqle) {
			logger.error("error executing insert for emp: " + emp);
		} finally {
			statement.close();
		}
		
		return emp;
	}
	
	// Update an employee that can currently be found in the database
	public static Employee update(Employee emp) throws SQLException {
		PreparedStatement statement = Connect().prepareStatement(
				"UPDATE Employees SET firstName = ?, lastName = ? WHERE id = ?;");
		
		statement.setString(1, emp.getFirstName());
		statement.setString(2, emp.getLastName());
		statement.setInt(3, emp.getId());
		try {
			statement.executeUpdate();
		} catch (SQLException sqle) {
			logger.error("error executing insert for emp: " + emp);
		} finally {
			statement.close();
		}
		
		return employeeMap.put(emp.getId(), emp);
	}
	
	// Delete/Remove an employee from the database
	public static Employee delete(int id) throws SQLException {
		PreparedStatement statement = Connect().prepareStatement(
				"DELETE FROM Employees where id = ?");
		statement.setInt(1, id);
		try {
			statement.executeUpdate();
		} catch (SQLException sqle) {
			logger.error("error executing delete for id: " + id);
		} finally {
			statement.close();
		}
		
		return employeeMap.remove(id);
	}
	

	// Method for adding employees to the cache
	private static Employee resultSetToEmployee(ResultSet rs) throws SQLException {
		Employee employee = null;

		String empId = rs.getString("id");
		// Is this Employee already in cache? If so, use it
		if (cache.containsKey(empId))
			employee = cache.get(empId);
		else
			employee = new Employee();

		employee.setId(rs.getInt("id"));
		employee.setFirstName(rs.getString("firstName"));
		employee.setLastName(rs.getString("lastName"));
		

		// add this employee to the cache
		if (!cache.containsKey(empId))
			cache.put(empId, employee);


		return employee;
	}
	
	// Get the number of employees in the database
	public static int getTotalRecords() {
		return employeeMap.size();
	}
	
}