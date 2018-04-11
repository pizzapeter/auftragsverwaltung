package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DepartmentManager {
    private static DepartmentManager ourInstance = new DepartmentManager();
    private ObservableList<Department> allDeps;

    public static DepartmentManager getInstance() {
        return ourInstance;
    }

    public ObservableList<Department> getAllDeps() {
        return allDeps;
    }

    public void setAllDeps(ArrayList<Department> allDeps) throws Exception {
        this.allDeps = FXCollections.observableArrayList();
        for (Department d :
                allDeps) {
            this.allDeps.add(d);
        }
    }
}
