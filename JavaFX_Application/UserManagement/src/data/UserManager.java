package data;

import java.util.HashSet;

public class UserManager {
    private static UserManager ourInstance = new UserManager();
    private HashSet<User> allUsers;

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
        allUsers = new HashSet<User>();
    }

    public void createUser(User newUser) throws Exception {
        // do some rest call
    }

    public void deleteUser(User userToDelete) throws Exception {
        // do some rest call
    }

    public void updateUser(User userToUpdate) throws Exception {
        // do some rest call
    }
}