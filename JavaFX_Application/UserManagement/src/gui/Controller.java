package gui;

import data.User;
import javafx.fxml.FXML;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    @FXML
    private javafx.scene.control.ListView<User> listView;
    private ArrayList<User> allUsers = new ArrayList<User>();

    @FXML
    public void initialize(){
        this.listView.getItems().add(new User("hans-peter", "peter-hans"));
    }

}
