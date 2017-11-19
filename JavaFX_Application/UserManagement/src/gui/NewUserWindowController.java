package gui;

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
    private ComboBox cbxDepartment;


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


        String id = "asdf";

        if (checkInputFields()) {

            User newUser = new User(tfFirstname.getText().trim(), tfLastname.getText().trim(), id.trim(), datePicker.getValue());
            try {
                UserManager.getInstance().createUser(newUser);
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

    private boolean checkInputFields() {
        boolean isOK = false;
        if (tfFirstname.getText().trim() == "" || tfLastname.getText().trim() == "" || datePicker.getValue() == null) {
            isOK = false;
        } else {
            isOK = true;
        }
        return isOK;
    }
}