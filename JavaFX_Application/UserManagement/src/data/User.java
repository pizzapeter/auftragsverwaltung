package data;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String date_of_birth;
    private int permissionLevel;
    private String department;
    private String password;

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getDepartment() {
        return department;
    }

    public String getPassword() {
        return password;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public User(int id, String firstname, String lastname, String dateofBirth, int permissionLevelID, String departmentName, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.date_of_birth = dateofBirth;
        this.permissionLevel = permissionLevelID;
        this.department = departmentName;
        this.password = password;
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

        if (id != user.id) return false;
        if (permissionLevel != user.permissionLevel) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
        if (date_of_birth != null ? !date_of_birth.equals(user.date_of_birth) : user.date_of_birth != null) return false;
        if (department != null ? !department.equals(user.department) : user.department != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (date_of_birth != null ? date_of_birth.hashCode() : 0);
        result = 31 * result + permissionLevel;
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}