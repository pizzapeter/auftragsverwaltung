package at.BSD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.ws.rs.GET;
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
	@Path("/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() {
		at.Data.Database db = new Database();
		String selectTableSQL = "SELECT t.ID from task t inner join EmployeeTask et on t.id = et.TaskID inner "
				+ "join Employees e on et.EmployeeID=e.ID;";
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
				jobject.put("Firstanme", rs.getObject("e.FIRSTNAME").toString());
				jobject.put("Lastname", rs.getObject("e.LASTNAME").toString());
				jobject.put("PLACEID", rs.getObject("PLACEID"));
				jarray.add(jobject);

			}
			db.getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

		return jarray.toJSONString();
	}
}
