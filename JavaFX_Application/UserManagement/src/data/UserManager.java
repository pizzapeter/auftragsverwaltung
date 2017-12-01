package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UserManager { // TODO: 20.10.2017 replace user methods with rest calls
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
                newUser.getDateOfBirth(), newUser.getPermissionLevel(), newUser.getDepartment()));
    }

    public void deleteUser(User userToDelete) throws Exception {
        this.allUsers.remove(userToDelete);
    }

    public void updateUser(User userToUpdate) throws Exception {
        // do some rest call
    }

    public void setAllUsers(ArrayList<User> allUsers) throws Exception {
        this.allUsers.clear();
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