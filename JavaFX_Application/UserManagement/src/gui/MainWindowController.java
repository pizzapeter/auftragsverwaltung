package gui;

import data.User;
import data.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private javafx.scene.control.ListView<User> listView;

    private ContextMenu contextMenu;


    @FXML
    public void initialize() {
        this.listView.getItems().addAll(UserManager.getInstance().getAllUsers());
        this.listView.getItems().add(new User("hans-peter", "peter-hans", "10"));
        this.listView.getItems().add(new User("hans-peter", "peter-hans", "10"));
        this.initContextMenu();
        System.out.println("ctx initialized");
    }

    @FXML
    public void OnCreateNewUser() {
        try {
            createNewUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initContextMenu() {
        this.contextMenu = new ContextMenu();

        MenuItem mNewUser = new MenuItem("new user");
        mNewUser.setOnAction(e -> {
            try {

                this.createNewUser();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        });

        MenuItem mDeleteUser = new MenuItem("delete user");
        mDeleteUser.setOnAction(e->{
            User u = this.listView.getSelectionModel().getSelectedItem();
            this.deleteUser();
        });

        MenuItem mEditItem = new MenuItem(("edit item"));
        mEditItem.setOnAction(e->{

        });

        this.contextMenu.getItems().addAll(mNewUser, mDeleteUser);
        this.listView.setContextMenu(this.contextMenu);
    }


    private void createNewUser() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NewUserWindow.fxml"));
        Stage newUserWindow = new Stage();
        newUserWindow.setScene(new Scene(root));
        newUserWindow.getIcons().add(new Image(MainWindowController.class.getResourceAsStream("ic_person_add_black_24dp_2x.png")));
        newUserWindow.setTitle("Create new User");
        newUserWindow.showAndWait();
        this.listView.getItems().clear();
        this.listView.getItems().addAll(UserManager.getInstance().getAllUsers());
        System.out.println("new user created");
    }

    private void deleteUser(){
        User u = this.listView.getSelectionModel().getSelectedItem();
        this.listView.getItems().remove(u);
    }
}
