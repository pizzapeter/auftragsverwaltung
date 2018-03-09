package at.BSD;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import at.Data.*;

public class UserLogic extends AbstractLogic {
	public List<Message> createUser(Employee emp) throws SQLException {
		List<Message> msg = new ArrayList<>();
		if (emp.username == null  || "".equals(emp.username.trim())) {
			msg.add(new Message(Message.Severity.ERROR, "username must be set"));
		}
		String insertSQL = "INSERT INTO employees (ID, firstname,lastname,date_of_birth,permissionLevelID ,departmentName,username,password) VALUES (employee_seq.nextval, ?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			java.util.Date today = new java.util.Date();
			Date d = new Date(today.getTime());
			preparedStatement = getConn().prepareStatement(insertSQL);
			preparedStatement.setString(1, emp.firstname);
			preparedStatement.setString(2,  emp.lastname);
			preparedStatement.setDate(3, d);
			preparedStatement.setInt(4, 1);
			preparedStatement.setString(5,  emp.departmentName);
			preparedStatement.setString(6,  emp.username);
			preparedStatement.setString(7,  emp.password);

			preparedStatement.executeUpdate();
			commit();
			msg.add(new Message(Message.Severity.INFO, "Successfully created"));
		} catch (SQLException e) {
			msg.add(new Message(Message.Severity.ERROR, "can't create user"));
			rollback();
			e.printStackTrace();
		} finally {
			close();
		}
		return msg;
	}
	public List<Employee> getAllEmployees(){
		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT id, firstname, lastname,permissionLevelID,departmentName,DATE_OF_BIRTH from employees";
		ArrayList<Employee> employees = new ArrayList<Employee>();
		ResultSet rs;
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				Employee p = new Employee(rs.getInt("id"),rs.getObject("firstname").toString(), rs.getObject("lastname").toString(), rs.getObject("departmentName").toString(), "", "", rs.getInt("permissionLevelID"),rs.getObject("DATE_OF_BIRTH").toString());
				employees.add(p);
			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return employees;
		
	}
	public List<Employee> getEmployeeByFirstname(String firstname){
		at.Data.Database db = new Database();
		Employee e = null;
		String selectTableSQL = "SELECT id, firstname,DATE_OF_BIRTH, lastname,permissionLevelID,departmentID from employees where firstname like ?";
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			PreparedStatement preparedStatement = db.getCon().prepareStatement(selectTableSQL);
			preparedStatement.setString(1, firstname);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				e = new Employee(rs.getInt("id"),rs.getObject("firstname").toString(), rs.getObject("lastname").toString(), rs.getObject("departmentID").toString(), null,null, rs.getInt("permissionLevelID"), rs.getObject("DATE_OF_BIRTH").toString());
				employees.add(e);
			}
			db.getCon().close();
		}
		catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
		return employees;
	}
}
