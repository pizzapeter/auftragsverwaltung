package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
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

    public ArrayList<Department> FetchAllDepartments() throws Exception {
        Gson gson = new GsonBuilder().create();
        String url = "departments";
        List<Department> allDeps;

        String fetchedData = fetchData(url);
        Department[] departments = gson.fromJson(fetchedData, Department[].class);
        allDeps = new ArrayList<>(Arrays.asList(departments));

        return (ArrayList<Department>) allDeps;
    }

    public ArrayList<PermissionLevel> FetchAllPermissionLevels() throws Exception {
        Gson gson = new GsonBuilder().create();
        String url = "permissionlevels";
        List<PermissionLevel> allPermissionLevels;

        String fetchedData = fetchData(url);
        PermissionLevel[] permissionLevels = gson.fromJson(fetchedData, PermissionLevel[].class);
        allPermissionLevels = new ArrayList<>(Arrays.asList(permissionLevels));

        return (ArrayList<PermissionLevel>) allPermissionLevels;
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
                throw new Exception("There is no data available");
        }
        connection.disconnect();
        return sb.toString();
    }

    public void CreateUser(User u) throws Exception {
        String ur = RESTUrl + "createUser";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(ur);

        // Create some NameValuePair for HttpPost parameters
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("firstname", u.getFirstname()));
        arguments.add(new BasicNameValuePair("lastname", u.getLastname()));
        arguments.add(new BasicNameValuePair("departmentName", u.getDepartment()));
        arguments.add(new BasicNameValuePair("dateOfBirth", u.getDate_of_birth()));
        arguments.add(new BasicNameValuePair("permissionLevelID", String.valueOf(u.getPermissionLevel())));
        arguments.add(new BasicNameValuePair("password", u.getPassword()));

        try {
            post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);

            // Print out the response message
            System.out.println("response from create user: " + EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String DeleteUser(String id) throws Exception {
        String url = RESTUrl + "deleteUserByID/" + id;
        URL ur = new URL(url);
        StringBuilder sb;

        HttpURLConnection connection = (HttpURLConnection) ur.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner sc = new Scanner(ur.openStream());
            sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
        }
        connection.disconnect();
        return sb.toString();
    }

    public String UpdateUser(User u) {
        String ur = RESTUrl + "updateUser";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(ur);

        // Create some NameValuePair for HttpPost parameters
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("id", String.valueOf(u.getId())));
        arguments.add(new BasicNameValuePair("firstname", u.getFirstname()));
        arguments.add(new BasicNameValuePair("lastname", u.getLastname()));
        arguments.add(new BasicNameValuePair("departmentName", u.getDepartment()));
        arguments.add(new BasicNameValuePair("dateOfBirth", u.getDate_of_birth()));
        arguments.add(new BasicNameValuePair("permissionLevelID", String.valueOf(u.getPermissionLevel())));
        arguments.add(new BasicNameValuePair("password", u.getPassword()));

        try {
            post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);

            // Print out the response message
            System.out.println("response from update user: " + EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean LogIn(String username, String password) {
        String ur = RESTUrl + "login";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(ur);

        // Create some NameValuePair for HttpPost parameters
        List<NameValuePair> arguments = new ArrayList<>(3);
        arguments.add(new BasicNameValuePair("username", username));
        arguments.add(new BasicNameValuePair("password", password));

        try {
            post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);

            // Print out the response message
            String ok = EntityUtils.toString(response.getEntity());
            if (ok.contains("true"))
                return true;
            else
                return false;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

}
