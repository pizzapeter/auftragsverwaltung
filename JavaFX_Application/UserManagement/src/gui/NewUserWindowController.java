package gui;

import data.RESTService;
import data.User;
import data.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

            User newUser = new User("42", tfFirstname.getText().trim(), tfLastname.getText().trim(), "25.01.2000", "1", "Test Deparment");
            try {
                RESTService.getInstance().PostUser(newUser);
                System.out.println(newUser.toString());
                stage.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill in all the input fields");
            alert.showAndWait();
        }
    }

    // 1 - chief
    // 2 - user


    private boolean checkInputFields() {
        boolean isOK = true
                ;
//        if (tfFirstname.getText().trim() == "" || tfLastname.getText().trim() == "" || datePicker.getValue() == null) {
//            isOK = false;
//        } else {
//            isOK = true;
//        }
        return isOK;
    }
}