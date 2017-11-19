package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginWindowController {
    @FXML
    private Label lblStatus;
    @FXML
    private PasswordField passwordField;

    private byte[] passwd; //only for testing

    public void OnLogin(ActionEvent actionEvent) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            passwd = digest.digest("password".getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest(passwordField.getText().getBytes(StandardCharsets.UTF_8));
            passwordField.clear();
          //  if (passwordField.getText() == "") {
                System.out.println("hashing worked");
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Logging you in");
                loadMainWindow();
            /*} else {
                lblStatus.setTextFill(Color.RED);
                lblStatus.setText("Wrong password!");
            }*/
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void loadMainWindow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Stage mainWindow = new Stage();
            mainWindow.setScene(new Scene(root));
            mainWindow.setTitle("User Management");
            mainWindow.getIcons().add(
                    new Image(Main.class.getResourceAsStream("ic_person_black_24dp_2x.png"))
            );
            Stage thisWindow = (Stage) lblStatus.getScene().getWindow();
            thisWindow.close();
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPassword() {
        boolean result = false;


        return result;
    }
}
