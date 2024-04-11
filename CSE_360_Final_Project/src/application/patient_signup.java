package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.FileWriter;
import java.io.IOException;

public class patient_signup extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main VBox for the layout
        VBox mainLayout = new VBox();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        mainLayout.setAlignment(Pos.TOP_CENTER); // Align at the top center
        mainLayout.setSpacing(20);

        // Create the signup heading
        Text heading = new Text("Patient Signup");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        heading.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(heading);
        headerPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        headerPane.setPadding(new Insets(10));
        headerPane.setPrefHeight(50); // Adjusting the height for the logo

        // Add the headerPane directly to the mainLayout without spacing
        mainLayout.getChildren().add(headerPane);

        // Create text fields for First Name, Last Name, Date of Birth, Phone Number, and Guardian Name
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField dobField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField guardianNameField = new TextField();

        // Set prompt text for text fields
        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        dobField.setPromptText("Date of Birth (MMYYDD)");
        phoneNumberField.setPromptText("Phone Number");
        guardianNameField.setPromptText("Guardian Name");

        // Set preferred width for text fields
        int prefWidth = 150;
        firstNameField.setPrefWidth(prefWidth);
        lastNameField.setPrefWidth(prefWidth);
        dobField.setPrefWidth(prefWidth);
        phoneNumberField.setPrefWidth(prefWidth);
        guardianNameField.setPrefWidth(prefWidth);

        // Arrange text fields and titles
        VBox textBoxes = new VBox(10);
        textBoxes.getChildren().addAll(
                new HBox(10, new Text("First Name"), firstNameField, new Text("Last Name"), lastNameField),
                new HBox(10, new Text("Date of Birth (MMYYDD)"), dobField, new Text("Phone Number"), phoneNumberField),
                new HBox(10, new Text("Guardian Name"), guardianNameField)
        );
        textBoxes.setAlignment(Pos.CENTER);

        // Create the buttons
        Button backButton = createButton("Back");
        Button submitButton = createButton("Submit");

        // Handle button actions
        backButton.setOnAction(e -> {
            primaryStage.close();
            new login_page().start(new Stage());
        });
        submitButton.setOnAction(e -> {
            if (fieldsAreFilled(firstNameField, lastNameField, dobField, phoneNumberField, guardianNameField)) {
                if (writeDataToFile(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobField.getText(),
                        phoneNumberField.getText(),
                        guardianNameField.getText())) {
                    showSuccessMessage("Patient successfully signed up!");
                } else {
                    showError("Failed to sign up patient. Please try again.");
                }
            } else {
                showError("Please fill in all fields.");
            }
        });

        // Add buttons to an HBox and add HBox to main layout
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(backButton, submitButton);
        buttonsBox.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(textBoxes, buttonsBox);

        // Create the scene
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital Patient Signup Page");
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        return button;
    }

    private boolean fieldsAreFilled(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean writeDataToFile(String firstName, String lastName, String dob, String phoneNumber, String guardianName) {
        String fileName = firstName + "_" + lastName + "_" + dob + ".txt";
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("First Name: " + firstName + "\n");
            writer.write("Last Name: " + lastName + "\n");
            writer.write("Date of Birth: " + dob + "\n");
            writer.write("Phone Number: " + phoneNumber + "\n");
            writer.write("Guardian Name: " + guardianName + "\n");
            writer.write("Visits: \n");
            writer.close();
            System.out.println("Data written to file '" + fileName + "' successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while writing data to file.");
            e.printStackTrace();
            return false;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
