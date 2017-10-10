package data;

import java.util.UUID;

public class User {
    private UUID id;
    private String firstname;
    private String lastname;

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.lastname;
    }
}
