package gui;

import data.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class NewUserWindowController {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;
    @FXML
    private TextField tfFirstname;
    @FXML
    private TextField tfLastname;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<PermissionLevel> cmbPermissionLvl;
    @FXML
    private ComboBox<Department> cmbDeps;

    @FXML
    public void initialize(){
        loadAllDeps();
        loadAllPermissionLevels();
    }

    private void loadAllPermissionLevels() {
        // runnable for that thread
        new Thread(() -> {
            try {
                ArrayList<PermissionLevel> all = RESTService.getInstance().FetchAllPermissionLevels();
                PermissionLevelManager.getInstance().setAllPermissionLevels(all);
                System.out.println("all permission levels loaded loaded");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Platform.runLater(() -> cmbPermissionLvl.setItems(PermissionLevelManager.getInstance().getAllPermissionLevels()));
        }).start();
    }


    private void loadAllDeps() {
        // runnable for that thread
        new Thread(() -> {
            try {
                ArrayList<Department> all = RESTService.getInstance().FetchAllDepartments();
                DepartmentManager.getInstance().setAllDeps(all);
                System.out.println("all deps loaded");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Platform.runLater(() -> cmbDeps.setItems(DepartmentManager.getInstance().getAllDeps()));
        }).start();
    }

    @FXML
    public void OnCancel(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void OnOK(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) btnOK.getScene().getWindow();
        System.out.println("date: " + datePicker.getValue());
        if (checkInputFields()) {
            PermissionLevel pl = cmbPermissionLvl.getSelectionModel().getSelectedItem();
            User newUser = new User(42, tfFirstname.getText().trim(), tfLastname.getText().trim(), datePicker.getValue().toString(), Integer.parseInt(pl.getID()), "Test Deparment", "passwd");

            new Thread() {

                // runnable for that thread
                public void run() {
                    try {
                        RESTService.getInstance().CreateUser(newUser);
                        System.out.println(newUser.toString());
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText(e.getMessage());
                        alert.showAndWait();
                        System.out.println(e.getMessage());
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.close();
                        }
                    });
                }
            }.start();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill in all the input fields");
            alert.showAndWait();
        }
    }

    // 1 - chief
    // 2 - user


    private boolean checkInputFields() {
        boolean isOK = true;
//        if (tfFirstname.getText().trim() == "" || tfLastname.getText().trim() == "" || datePicker.getValue() == null) {
//            isOK = false;
//        } else {
//            isOK = true;
//        }
        return isOK;
    }
}