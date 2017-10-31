package data;

import java.time.LocalDate;

public class User {

    private String id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String department;
    private String username;


    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDepartment() {
        return department;
    }

    public String getUsername() {
        return username;
    }

    public User(String firstname, String lastname, String id, LocalDate dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        return lastname != null ? lastname.equals(user.lastname) : user.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
}