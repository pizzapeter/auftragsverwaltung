package gui;

import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private javafx.scene.control.ListView<User> listView;
    private ArrayList<User> allUsers = new ArrayList<User>();


    @FXML
    public void initialize(){
        this.listView.getItems().add(new User("hans-peter", "peter-hans", "10"));
    }

    @FXML
    public void createNewUser(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("NewUserWindow.fxml"));
            Stage newUserWindow = new Stage();
            newUserWindow.setScene(new Scene(root));
            newUserWindow.getIcons().add(new Image(Controller.class.getResourceAsStream("ic_person_add_black_24dp_2x.png")));
            newUserWindow.setTitle("Create new User");
            newUserWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
