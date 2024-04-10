package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class patient_view extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Text message = new Text("Welcome to the Patient's View!");
        root.getChildren().add(message);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Patient's View");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

