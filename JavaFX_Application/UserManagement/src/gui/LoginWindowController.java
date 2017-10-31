package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;

public class LoginWindowController {
    @FXML
    private Label lblStatus;

    @FXML
    private PasswordField passwordField;

    public void OnLogin(ActionEvent actionEvent) {


        lblStatus.setTextFill(Color.RED);
        lblStatus.setText("Wrong Password");
    }


    private boolean checkPassword() {
        boolean result = false;


        return result;
    }
}
