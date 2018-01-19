package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RESTService {
    private static RESTService ourInstance = new RESTService();
    private static final String RESTUrl = "http://localhost:8080/RESTBSD/rest/UserService/";

    public static RESTService getInstance() {
        return ourInstance;
    }


    public ArrayList<User> FetchAllUsers() throws Exception {
        Gson gson = new GsonBuilder().create();
        String url = "users";
        List<User> allUsers;

        String fetchedData = fetchData(url);

        User[] userArray = gson.fromJson(fetchedData, User[].class);
        allUsers = new ArrayList<>(Arrays.asList(userArray));
        return (ArrayList<User>) allUsers;
    }


    private String fetchData(String u) throws Exception {
        URL url;
        int responseCode;
        StringBuilder sb;

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


    public void PostUser(User u) throws Exception {
        String ur = RESTUrl + "createUser";
        String ret = "";

        HttpPost httpPost = new HttpPost(ur);
        List<BasicNameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("firstname", u.getFirstname()));
        params.add(new BasicNameValuePair("lastname", u.getLastname()));
        params.add(new BasicNameValuePair("departmentName", u.getDepartment()));
        params.add(new BasicNameValuePair(" dateOfBirth", u.getDateOfBirth()));
        params.add(new BasicNameValuePair("permissionLevelID", String.valueOf(u.getPermissionLevel())));
        params.add(new BasicNameValuePair("password", u.getPassword())));


        URL url = new URL(ur);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        String input = "{\"ID\":1,\"Namen\":\"Traingsstadion\",\"ClubID\":1,\"StadiumSize\":100}";

        Gson gson = new GsonBuilder().create();
        gson.toJson(u);

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        while ((output = br.readLine()) != null) {
            ret = output;
        }

        conn.disconnect();
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
