package gui;

import data.RESTService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginWindowController {
    @FXML
    private Label lblStatus;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    private JPasswordField jPasswordField;

    private byte[] passwd; //only for testing

    public void OnLogin(ActionEvent actionEvent) {
        try {


            boolean loggedIn = RESTService.getInstance().LogIn(usernameField.getText(), passwordField.getText());
            passwordField.clear();
          if (loggedIn) {
                System.out.println("loggin you in");
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Logging you in");
                loadMainWindow();
            } else {
                lblStatus.setTextFill(Color.RED);
                lblStatus.setText("Wrong password!");
            }
        } catch (Exception e) {
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
