package gui;

import data.RESTService;
import data.User;
import data.UserManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.util.ArrayList;

public class MainWindowController {

    //region FXML Controls
    @FXML
    private javafx.scene.control.ListView<User> listView;

    @FXML
    private CustomTextField searchTextField;

    //endregion

    //region private vars
    private ContextMenu contextMenu;
    private ObservableList<User> filteredList = FXCollections.observableArrayList();
    //endregion

    @FXML
    public void initialize() {
        Thread t = new Thread(()->{
            showLoading(true);
            loadAllUser();
            showLoading(false);
            this.listView.setItems(UserManager.getInstance().getAllUsers());
        });
        t.start();

        this.listView.setItems(UserManager.getInstance().getAllUsers());
        this.initContextMenu();
        this.addKeyListener();
        this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            User u = listView.getSelectionModel().getSelectedItem();
            if (u != null) {
                System.out.println("item selected");
            }
        });
    }

    private void showLoading(boolean b) {


    }

    @FXML
    public void OnCreateNewUser() {
        try {
            createNewUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addKeyListener() {
        this.searchTextField.setOnKeyTyped(event -> {
            filterList();
        });
    }

    private void filterList() {
        String searchQuery = this.searchTextField.getText();
        this.filteredList.clear();
        for (User u :
                UserManager.getInstance().getAllUsers()) {
            if (searchQuery == "") {
                setItemSource(UserManager.getInstance().getAllUsers());
            }
            if (u.getFirstname().toLowerCase().contains(searchQuery.toLowerCase()) || u.getLastname().toLowerCase().contains(searchQuery.toLowerCase())) {
                this.filteredList.add(u);
                Platform.runLater(() -> {
                    setItemSource(filteredList);
                });
            }
            if (this.filteredList.size() == 0) {
                Platform.runLater(() -> {
                    this.searchTextField.setStyle("-fx-background-color: red");
                });
            } else {
                Platform.runLater(() -> {
                    this.searchTextField.setStyle("-fx-background-color: white");
                });
            }
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
        mDeleteUser.setOnAction(e -> {
            this.deleteUser();
        });

        MenuItem mEditItem = new MenuItem(("edit item"));
        mEditItem.setOnAction(e -> {

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
        System.out.println("new user created");
        loadAllUser();
    }

    private void loadAllUser(){
        try {
            ArrayList<User> all = RESTService.getInstance().FetchAllUsers();
            UserManager.getInstance().setAllUsers(all);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void deleteUser() {
        User u = this.listView.getSelectionModel().getSelectedItem();
        try {
            UserManager.getInstance().deleteUser(u);
            this.resetSearch(); // maybe there's a better approach :thinking:
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onCloseSearch() {
        this.resetSearch();
    }

    public void resetSearch() {
        this.searchTextField.clear();
        setItemSource(UserManager.getInstance().getAllUsers());
    }

    private void setItemSource(ObservableList<User> itemSource) {
        this.listView.setItems((itemSource));
    }

    public void onRefresh() {
        loadAllUser();
    }
}
