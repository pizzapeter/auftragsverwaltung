package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene((new Scene(root)));
        primaryStage.getIcons().add(
                new Image(Main.class.getResourceAsStream("ic_vpn_key_black_24dp_2x.png"))
        );
        primaryStage.show();
        primaryStage.setResizable(false);

//        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
//        primaryStage.setTitle("Fancy User Management");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.getIcons().add(
//                new Image(Main.class.getResourceAsStream("ic_person_black_24dp_2x.png"))
//        );
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
