package gui;

import data.User;
import data.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.UUID;

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

        User newUser = new User(tfFirstname.getText(), tfLastname.getText(), id);
        try {
            UserManager.getInstance().createUser(newUser);
            System.out.println(newUser.toString());
            stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }
}
