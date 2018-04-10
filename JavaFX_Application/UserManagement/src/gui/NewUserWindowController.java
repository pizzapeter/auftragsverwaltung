package gui;

import data.RESTService;
import data.User;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public void OnCancel(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void OnOK(ActionEvent actionEvent) {
        Stage stage;
        stage = (Stage) btnOK.getScene().getWindow();

        if (checkInputFields()) {
            User newUser = new User(42, tfFirstname.getText().trim(), tfLastname.getText().trim(), "25.01.2000", 1, "Test Deparment", "passwd");

            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            //Background work
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        try {
                                            RESTService.getInstance().CreateUser(newUser);
                                            System.out.println(newUser.toString());
                                            stage.close();
                                        } catch (Exception e) {
                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                            alert.setHeaderText(e.getMessage());
                                            alert.showAndWait();
                                        }
                                    } finally {
                                        latch.countDown();
                                    }
                                }
                            });
                            latch.await();
                            //Keep with the background work
                            return null;
                        }
                    };
                }
            };
            service.start();

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