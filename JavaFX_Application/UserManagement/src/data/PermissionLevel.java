package data;

public class PermissionLevel {
    private String ID;
    private String Name;

    public PermissionLevel(String ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return this.ID;
        //return this.ID + " " + this.Name;
    }
}
