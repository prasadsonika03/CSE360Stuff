package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NurseView extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Text message = new Text("Welcome to the Nurse's View!");
        root.getChildren().add(message);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Nurse's View");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

