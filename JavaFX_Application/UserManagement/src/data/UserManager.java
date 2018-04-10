package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UserManager {
    private static UserManager ourInstance = new UserManager();
    private ObservableList<User> allUsers;

    public static UserManager getInstance() {
        return ourInstance;
    }

    public ObservableList<User> getAllUsers() {
        return allUsers;
    }

    private UserManager() {
        allUsers = FXCollections.observableArrayList();
    }

    public void createUser(User newUser) throws Exception {
        if (this.doesUserExist(newUser)) {
            throw new Exception("User does already exist");
        }
        this.allUsers.add(new User(newUser.getId(), newUser.getFirstname(), newUser.getLastname(),
                newUser.getDateOfBirth(), newUser.getPermissionLevel(), newUser.getDepartment(), "passwd"));
    }

    public void deleteUser(User userToDelete) throws Exception {
      //  this.allUsers.remove(userToDelete);
        String response = RESTService.getInstance().DeleteUser( String.valueOf(userToDelete.getId()));
        System.out.println("response of delete user: " + response);
    }

    public void updateUser(User userToUpdate) throws Exception {

    }

    public void setAllUsers(ArrayList<User> allUsers) throws Exception {
        this.allUsers = FXCollections.observableArrayList();
        for (User u :
                allUsers) {
            UserManager.getInstance().createUser(u);
        }
    }


    private boolean doesUserExist(User toCheck) {
        boolean doesExist = true;
        if (!this.allUsers.contains(toCheck)) {
            doesExist = false;
        }
        return doesExist;
    }
}