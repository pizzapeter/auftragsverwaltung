package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PermissionLevelManager {
    private static PermissionLevelManager ourInstance = new PermissionLevelManager();
    private ObservableList<PermissionLevel> allPermissionLevels;

    public static PermissionLevelManager getInstance() {
        return ourInstance;
    }

    public ObservableList<PermissionLevel> getAllPermissionLevels() {
        return allPermissionLevels;
    }

    public void setAllPermissionLevels(ArrayList<PermissionLevel> allPermissionLevels) throws Exception {
        this.allPermissionLevels = FXCollections.observableArrayList();
        for (PermissionLevel p :
                allPermissionLevels) {
            this.allPermissionLevels.add(p);
        }
    }
}
