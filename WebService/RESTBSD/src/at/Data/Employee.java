package at.Data;

import java.time.LocalDateTime;

public class Employee {
	public String firstname;
	public String lastname;
	public String departmentName;
	public String username;
	public String password;
	public int permissonLevelID;
	public String date_of_birth;
	public int ID;
	
	
	
	public Employee(int ID,String firstname, String lastname, String departmentName, String username, String password,
			int permissonLevelID, String date_of_birth) {
		super();
		this.ID = ID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.departmentName = departmentName;
		this.username = username;
		this.password = password;
		this.permissonLevelID = permissonLevelID;
		this.date_of_birth = date_of_birth;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPermissonLevelID() {
		return permissonLevelID;
	}
	public void setPermissonLevelID(int permissonLevelID) {
		this.permissonLevelID = permissonLevelID;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	
}
