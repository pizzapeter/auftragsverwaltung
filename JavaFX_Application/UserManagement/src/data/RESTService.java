package data;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RESTService {
    private static RESTService ourInstance = new RESTService();
    private static final String RESTUrl = "http://localhost:8080/RESTBSD/rest/UserService/";

    public static RESTService getInstance() {
        return ourInstance;
    }

    public User FetchUserByID(int id) throws Exception {
        String url = "userbyid/" + Integer.toString(id);
        String fetchedData = fetchData(url);
        User fetchedUser = parseJSON(fetchedData).get(0);

        return fetchedUser;
    }

    public ArrayList<User> FetchAllUsers() throws Exception {
        String url = "users";
        String JSONString = fetchData(url);
        return parseJSON(JSONString);
    }


    private String fetchData(String u) throws Exception {
        URL url;
        int responseCode;
        StringBuilder sb = null;

        url = new URL(RESTUrl + u);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner sc = new Scanner(url.openStream());
            sb = new StringBuilder();

            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            if (sb.length() < 1)
                throw new Exception("There are no users available");
        }
        connection.disconnect();
        return sb.toString();
    }

    private ArrayList<User> parseJSON(String json) {
        JSONParser parser = new JSONParser();
        ArrayList<User> ret = null;

        try {
            Object obj = parser.parse(json);

            JSONArray jsonArray = (JSONArray) obj;
            System.out.println(jsonArray.size());

            ret = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ret.add(parseJSONObject(jsonObject));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return ret;
    }

    private User parseJSONObject(JSONObject obj) throws Exception {
        return new User(obj.get("id").toString(), obj.get("firstname").toString(), obj.get("lastname").toString(), obj.get("birthday").toString(),
                obj.get("permissionLevel").toString(), obj.get("department").toString());
    }

    public void PostUser(User u) throws Exception {
        String ur = RESTUrl + "createUSER";
        String urlParameters = "firstname=" + u.getFirstname()
                + "&lastname=" + u.getLastname()
                + "&date_of_birth=" + u.getDateOfBirth()
                + "&permissionLevelID=" + u.getPermissionLevel()
                + "&departmentName=" + u.getDepartment()
                + "&password=hallo"
                + "&username=BATMAN";


        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(ur);
        ArrayList<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("firstname", u.getFirstname()));
        params.add(new BasicNameValuePair("lastname", u.getLastname()));
        params.add(new BasicNameValuePair("id", u.getId()));

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();

        System.out.println("response: " + response.toString());


        //  OutputStreamWriter wr = new OutputStreamWriter()


    }

    public void DeleteUser(String id) throws Exception {
        String url = RESTUrl + "/deletUserByID/" + id;

    }

    private HttpURLConnection getConnection(String _url, String requestMethod, int length) throws Exception {
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(false);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(length));
        connection.setUseCaches(false);
        connection.connect();
        return connection;
    }

}
