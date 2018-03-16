package at.BSD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import at.Data.Database;

@Path("/TaskService")
public class TaskService {
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/getalltasks")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTasks() {
		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT t.ID,t.NAME,t.Description from task t inner join EmployeeTask et on t.id = et.TaskID inner join Employees e on et.EmployeeID=e.ID";
		JSONArray jarray = new JSONArray();
		JSONObject jobject = null;
		ResultSet rs;
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				jobject = new JSONObject();
				jobject.put("ID", rs.getObject("t.ID"));
				jobject.put("Name", rs.getObject("t.NAME"));
				jobject.put("DESCRIPTION", rs.getObject("t.DESCRIPTION"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return jarray.toJSONString();
	}
		
		
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/gettasksbyemployee/{employeeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaksOfEmployee(@PathParam("employeeID") String employeeID) {
		at.Data.Database db = new Database();
		StringBuilder st = new StringBuilder();
		st.append("SELECT t.ID,t.NAME,t.Description from task t inner join ");
		st.append("EmployeeTask et on t.id = et.TaskID inner join Employees e on ");
		st.append("et.EmployeeID=e.ID where t.Finished = 0 and et.EMPLOYEEID = "+employeeID);
		String selectTableSQL = st.toString();
				
		JSONArray jarray = new JSONArray();
		JSONObject jobject = null;
		ResultSet rs;
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				jobject = new JSONObject();
				jobject.put("ID", rs.getObject("ID"));
				jobject.put("NAME", rs.getObject("NAME"));
				jobject.put("Description", rs.getObject("Description"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return jarray.toJSONString();
	}
	@SuppressWarnings("unchecked")
	@GET
	@Path("/gettaskbyplace/{placeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTaskbyPlace(@PathParam("placeID") String placeID) {
		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT t.ID from task t inner join EmployeeTask et on t.id = et.TaskID and t.PLACEID = "+placeID +" inner "
				+ "join Employees e on et.EmployeeID=e.ID";
		JSONArray jarray = new JSONArray();
		JSONObject jobject = null;
		ResultSet rs;
		try {
			Statement statement = db.getCon().createStatement();
			rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				jobject = new JSONObject();
				jobject.put("ID", rs.getObject("t.ID"));
				jobject.put("Name", rs.getObject("t.NAME"));
				jobject.put("DESCRIPTION", rs.getObject("t.DESCRIPTION"));
				jobject.put("FINISHED", rs.getObject("t.FINISHED"));
				jobject.put("Firstname", rs.getObject("e.FIRSTNAME").toString());
				jobject.put("Lastname", rs.getObject("e.LASTNAME").toString());
				jobject.put("PLACEID", rs.getObject("PLACEID"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return jarray.toJSONString();
	}
	@POST
	@Path("/insert")
	public String insertTask(@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("PlaceID") String PlaceID) {
		at.Data.Database db = new Database();
		String insertSQL = "INSERT INTO Task (ID, name,description,finished,PlaceID) VALUES (task_seq.nextval,?,?,0,?)";
		PreparedStatement preparedStatement;
		try {
		preparedStatement = db.getCon().prepareStatement(insertSQL);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, description);
		preparedStatement.setInt(3, Integer.parseInt(PlaceID));

		preparedStatement.executeUpdate();
		db.getCon().commit();
		db.getCon().close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Inserted";
	}
	
	@GET
	@Path("/finishtask/{TaskID}")
	@Produces(MediaType.TEXT_PLAIN)
	public String finishTask(@PathParam("TaskID") String taskID) {
		at.Data.Database db = new Database();
		String updateCommand = "UPDATE task SET finished = 1 WHERE ID = " + taskID;
		try {
			Statement statment = db.getCon().createStatement();
			statment.execute(updateCommand);
			return "updatet";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
	}
	
}
