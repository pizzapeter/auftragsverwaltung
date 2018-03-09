package at.BSD;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.GsonBuilder;

import at.Data.Database;
import at.Data.Employee;
import at.Data.Message;

@Path("/UserService")
public class UserService {

	@GET
	@Path("/deleteUserByID/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletUserByID(@PathParam("id") int ID) {
		at.Data.Database db = new Database();
		String returnString = "Deleted";
		String selectTableSQL = "DELETE FROM employees WHERE ID = " + ID;
		
		try {
			Statement statement = db.getCon().createStatement();
			statement.executeQuery(selectTableSQL);
			db.getCon().commit();
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			returnString = "Error";
		}
		return returnString;
	}

	@GET
	@Path("/userbyFirstname/{firstname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsersByFirstname(@PathParam("firstname") String firstname) {
		//List<Employee> employees = logic.getEmployeeByFirstname(firstname);
		//return toJson(employees);
		return firstname;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/userbyid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsersByID(@PathParam("id") int ID) {

		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT id, firstname, lastname,permissionLevelID,departmentName,DATE_OF_BIRTH from employees where id="
				+ ID;
		JSONArray jarray = new JSONArray();
		JSONObject jobject = null;
		ResultSet rs;
		System.out.println("ID: " + ID);
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				jobject = new JSONObject();
				jobject.put("id", rs.getObject("id"));
				jobject.put("firstname", rs.getObject("firstname"));
				jobject.put("lastname", rs.getObject("lastname"));
				jobject.put("birthday", rs.getObject("DATE_OF_BIRTH").toString());
				jobject.put("permissionLevel", rs.getObject("permissionLevelID"));
				jobject.put("department", rs.getObject("departmentName"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jarray.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/departments")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllDeparment() {

		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT name from department";
		JSONArray jarray = new JSONArray();
		JSONObject jobject = null;
		ResultSet rs;
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				jobject = new JSONObject();
				jobject.put("name", rs.getObject("name"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return jarray.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/permissionlevels")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPermissionLevels() {

		at.Data.Database db = new Database();
		String selectTableSQL = "select ID,Description from PermissionLevel";
		JSONArray jarray = new JSONArray();
		JSONObject jobject = null;
		ResultSet rs;
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);
			while (rs.next()) {
				jobject = new JSONObject();
				jobject.put("ID", rs.getObject("ID"));
				jobject.put("Name", rs.getObject("Description"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return jarray.toJSONString();
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() {
		UserLogic logic = new UserLogic();
		List<Employee> employees = logic.getAllEmployees();
		
		return toJson(employees);
	}

	@POST
	@Path("/asdf")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(String input) {
		Employee emp = fromJson(input, Employee.class);
		UserLogic logic = new UserLogic();
		List<Message> list;
		try {
			list = logic.createUser(emp);
		} catch (SQLException e) {
			list = new ArrayList<>();
			list.add(new Message(Message.Severity.ERROR, e.getMessage(), true));
		}

		return toJson(list.toArray());
	}

	private static String toJson(Object data) {
		return new GsonBuilder().create().toJson(data);
	}

	private static <T> T fromJson(String data, Class<T> targetClass) {
		return new GsonBuilder().create().fromJson(data, targetClass);
	}

	@POST
	@Path("/createUser")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String creatNewUser(@FormParam("firstname") String firstname,
											@FormParam("lastname") String lastname,
											@FormParam("departmentName") String departmentName,
											@FormParam("dateOfBirth") String dateOfBirth,
											@FormParam("permissionLevelID") String permissionLevelID,
											@FormParam("password") String password) {
		at.Data.Database db = new Database();
		
		
		String insertSQL = "INSERT INTO employees (ID, firstname,lastname,date_of_birth,permissionLevelID ,departmentName,username,password) VALUES (employee_seq.nextval, ?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			java.util.Date today = new java.util.Date();
			Date d = new Date(today.getTime());
			preparedStatement = db.getCon().prepareStatement(insertSQL);
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setDate(3, d);
			preparedStatement.setInt(4, Integer.parseInt(permissionLevelID));
			preparedStatement.setString(5, departmentName);
			preparedStatement.setString(6, "asdf"); // username
			preparedStatement.setString(7, "yxcv"); // password

			preparedStatement.executeUpdate();
			db.getCon().commit();
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
			//System.out.println("Error: " + e.getMessage());
			//return "fuck";
		}
		return "Inserted";
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String login(@FormParam("username") String username, @FormParam("password") String password) {
		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT password,ID from employees where username = ?";

		PreparedStatement preparedStatement = null;
		JSONObject jobject = null;
		try {
			preparedStatement = db.getCon().prepareStatement(selectTableSQL);
			preparedStatement.setString(1, username);
			jobject = new JSONObject();
			ResultSet rs = preparedStatement.executeQuery();
			boolean exist = false;
			while (rs.next()) {
				exist = true;
				String passwordOfTable = rs.getString("password");
				System.out.println(passwordOfTable);
				System.out.println(password);
				if (passwordOfTable.equals(password)) {
					jobject.put("Ok", true);
					jobject.put("ID", rs.getString("ID"));
				} else {
					jobject.put("OK", false);
					jobject.put("Error", "Password");
				}
			}
			if (!exist) {
				jobject.put("OK", false);
				jobject.put("Error", "username");
			}
			db.getCon().commit();
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}

		return jobject.toJSONString();
	}
	@POST
	@Path("/updateUser")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateUser(@FormParam("id") String ID,@FormParam("firstname") String firstname,
											@FormParam("lastname") String lastname,
											@FormParam("departmentName") String departmentName,
											@FormParam("dateOfBirth") String dateOfBirth,
											@FormParam("permissionLevelID") String permissionLevelID) {
		at.Data.Database db = new Database();
		
		
		String insertSQL = "UPDATE employees SET firstname = ?,lastname = ?,"
				+ "departmentName = ?,date_of_birth = ?,permissionLevelID = ? WHERE id = ?";
		PreparedStatement preparedStatement;
		try {
			java.util.Date today = new java.util.Date();
			Date d = new Date(today.getTime());
			preparedStatement = db.getCon().prepareStatement(insertSQL);
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setString(3, departmentName);
			preparedStatement.setDate(4, d);
			preparedStatement.setInt(5, Integer.parseInt(permissionLevelID));

			preparedStatement.setInt(6, Integer.parseInt(ID));

			preparedStatement.executeUpdate();
			db.getCon().commit();
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
			//System.out.println("Error: " + e.getMessage());
			//return "fuck";
		}
		return "Updatet";
	}


}