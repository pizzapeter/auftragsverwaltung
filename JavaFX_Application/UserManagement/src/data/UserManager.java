package data;

import java.util.HashSet;

public class UserManager { // TODO: 20.10.2017 replace user methods with rest calls
    private static UserManager ourInstance = new UserManager();
    private HashSet<User> allUsers;

    public static UserManager getInstance() {
        return ourInstance;
    }

    public HashSet<User> getAllUsers() {
        return allUsers;
    }

    private UserManager() {
        allUsers = new HashSet<>();
    }

    public void createUser(User newUser) throws Exception {
        // do some rest call
        if (this.doesUserExist(newUser)) {
            throw new Exception("User does already exist");
        }
        this.allUsers.add(new User(newUser.getFirstname(), newUser.getLastname(), newUser.getId(), newUser.getDateOfBirth()));
    }

    public void deleteUser(User userToDelete) throws Exception {
        // do some rest call
    }

    public void updateUser(User userToUpdate) throws Exception {
        // do some rest call
    }

    private boolean doesUserExist(User toCheck) {
        boolean doesExist = true;
        if (!this.allUsers.contains(toCheck)) {
            doesExist = false;
        }
        return doesExist;
    }
}