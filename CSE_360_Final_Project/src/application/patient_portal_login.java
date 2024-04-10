package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
import java.io.File;

public class patient_portal_login extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main VBox for the layout
        VBox mainLayout = new VBox();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        mainLayout.setAlignment(Pos.TOP_CENTER); // Align at the top center
        mainLayout.setSpacing(20);

        // Create the login heading
        Text heading = new Text("Patient Portal");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        heading.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(heading);
        headerPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        headerPane.setPadding(new Insets(10));
        headerPane.setPrefHeight(50); // Adjusting the height for the logo

        // Add the headerPane directly to the mainLayout without spacing
        mainLayout.getChildren().add(headerPane);

        // Create text fields for First Name, Last Name, and Date of Birth
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField dobField = new TextField();

        // Set prompt text for text fields
        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        dobField.setPromptText("Date of Birth (MMYYDD)");

        // Set preferred width for text fields
        firstNameField.setPrefWidth(150);
        lastNameField.setPrefWidth(150);
        dobField.setPrefWidth(150);

        // Arrange text fields and titles
        VBox textBoxes = new VBox(10);
        textBoxes.getChildren().addAll(
                new HBox(10, new Text("First Name"), firstNameField, new Text("Last Name"), lastNameField),
                new VBox(10, new Text("Date of Birth (MMYYDD)"), dobField)
        );
        textBoxes.setAlignment(Pos.CENTER);

        // Create the buttons
        Button backButton = createButton("Back");
        Button signUpButton = createButton("Signup");
        Button submitButton = createButton("Submit");

        // Handle button actions
        backButton.setOnAction(e -> {
            primaryStage.close();
            new login_page().start(new Stage());
        });
        
        signUpButton.setOnAction(e -> {
            primaryStage.close();
            new patient_signup().start(new Stage());
        });
        
        submitButton.setOnAction(e -> {
            // Check if user is present using patientinfo.txt
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dob = dobField.getText();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !dob.isEmpty()) {
                if (isUserPresent(firstName, lastName, dob)) {
                    // Code to guide user to patient's view goes here
                	primaryStage.close(); // Close the current login page
                    patient_view patientView = new patient_view();
                    Stage patientStage = new Stage();
                    patientView.start(patientStage); // Display the patient's view
                    System.out.println("User found, guiding to patient's view.");
                    // Replace System.out.println() with code to open patient_view.java
                } else {
                    // Pop error message if user is not present
                    showError("User not found. Please sign up first.");
                }
            } else {
                showError("Please fill in all the fields.");
            }
        });


        // Add buttons to an HBox and add HBox to main layout
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(backButton, signUpButton, submitButton);
        buttonsBox.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(textBoxes, buttonsBox);

        // Create the scene
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital Patient Login Page");
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        return button;
    }

    private boolean isUserPresent(String firstName, String lastName, String dob) {
        // Construct the filename based on the entered details
        String filename = firstName + "_" + lastName + "_" + dob + ".txt";

        // Construct the file object
        File file = new File(filename);

        // Check if the file exists
        return file.exists();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
