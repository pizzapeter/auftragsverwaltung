package gui;

import data.RESTService;
import data.User;
import data.UserManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainWindowController {
    @FXML
    private javafx.scene.control.ListView<User> listView;

    @FXML
    private CustomTextField searchTextField;
    @FXML
    private TextField tfFirstname;
    @FXML
    private TextField tfLastname;
    @FXML
    private TextField tfPermissionLevel;
    @FXML
    private TextField tfDepartment;
    @FXML
    private Button btnSave;

    private ContextMenu contextMenu;
    private ObservableList<User> filteredList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadAllUser();

        this.listView.setItems(UserManager.getInstance().getAllUsers());
        this.initContextMenu();
        this.addKeyListener();
        this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            User u = listView.getSelectionModel().getSelectedItem();
            if (u != null) {
                setInputFields(u);
                System.out.println("item selected");
            }
        });
    }

    private void showLoading(boolean b) {


    }

    private void setInputFields(User user){
        try {
            tfFirstname.setText(user.getFirstname());
            tfLastname.setText(user.getLastname());
            tfDepartment.setText(user.getDepartment());
            tfPermissionLevel.setText(String.valueOf(user.getPermissionLevel()));
            btnSave.setDisable(true);
            disableGUIElements();
        }catch (Exception ex){
            System.out.println("setinputfield: " +ex.getMessage());
        }
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

    private void loadAllUser() {
        // runnable for that thread
        new Thread(() -> {
            try {
                ArrayList<User> all = RESTService.getInstance().FetchAllUsers();
                UserManager.getInstance().setAllUsers(all);
                System.out.println("all users loaded");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Platform.runLater(() -> listView.setItems(UserManager.getInstance().getAllUsers()));
        }).start();
    }

    private void deleteUser() {
        User u = this.listView.getSelectionModel().getSelectedItem();
        new Thread() {

            // runnable for that thread
            public void run() {
                try {
                    UserManager.getInstance().deleteUser(u);
                    loadAllUser();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        resetSearch();
                    }
                });
            }
        }.start();
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

    public void OnEdit() {
        enableGUIElements();
        //editUser();
    }

    private void editUser(){
        User u = listView.getSelectionModel().getSelectedItem();
        User editedUser = new User(u.getId(), tfFirstname.getText().trim(), tfLastname.getText().trim(), "2018-12-04", Integer.parseInt(tfPermissionLevel.getText()), "Test Deparment", "passwd");
        // runnable for that thread
        new Thread(() -> {
            try {
                RESTService.getInstance().UpdateUser(editedUser);
                System.out.println(editedUser.toString());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
                System.out.println(e.getMessage());
            }

            Platform.runLater(() -> loadAllUser());
        }).start();
    }

    private void enableGUIElements(){
        tfFirstname.setDisable(false);
        tfLastname.setDisable(false);
        tfDepartment.setDisable(false);
        tfDepartment.setText("Test Department");
        tfPermissionLevel.setDisable(false);
        btnSave.setDisable(false);
    }

    private void disableGUIElements(){
        tfFirstname.setDisable(true);
        tfLastname.setDisable(true);
        tfDepartment.setDisable(true);
        tfPermissionLevel.setDisable(true);
        btnSave.setDisable(true);
    }

    public void OnSave() {
        editUser();
        disableGUIElements();
    }
}
