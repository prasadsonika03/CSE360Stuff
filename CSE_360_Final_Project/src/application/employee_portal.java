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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class employee_portal extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main VBox for the layout
        VBox mainLayout = new VBox();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        mainLayout.setAlignment(Pos.TOP_CENTER); // Align at the top center
        mainLayout.setSpacing(20);

        // Create the login heading
        Text heading = new Text("Employee Portal");
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
        TextField employeeidField = new TextField();

        // Set prompt text for text fields
        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        employeeidField.setPromptText("Employee ID");

        // Set preferred width for text fields
        firstNameField.setPrefWidth(150);
        lastNameField.setPrefWidth(150);
        employeeidField.setPrefWidth(150);

        // Arrange text fields and titles
        VBox textBoxes = new VBox(10);
        textBoxes.getChildren().addAll(
                new HBox(10, new Text("First Name"), firstNameField, new Text("Last Name"), lastNameField),
                new VBox(10, new Text("Employee ID"), employeeidField)
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
            String employeeID = employeeidField.getText().trim();
            // Check if user is present using employeeID
            if (isUserPresent(employeeID)) {
                // Code to guide user to respective view based on employeeID
                if (employeeID.startsWith("N")) {
                    // Nurse view
                    openNurseView();
                } else if (employeeID.startsWith("D")) {
                    // Doctor view
                    openDoctorView();
                    System.out.println("Opening Doctor View");
                }
            } else {
                // Pop error message if user is not present
                showError("User not found.");
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
        primaryStage.setTitle("Hospital Patient Login Page");
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        return button;
    }

    private boolean isUserPresent(String employeeID) {
        String filePath = employeeID + ".txt"; // Assuming the file name corresponds to the employee ID
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Check if file exists
            return true;
        } catch (IOException e) {
            // File not found or error reading file
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
    
    private void openNurseView() {
        // Create an instance of NurseView and call its start() method
        NurseView nurseView = new NurseView();
        try {
            nurseView.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openDoctorView() {
        // Create an instance of DoctorView and call its start() method
        DoctorView doctorView = new DoctorView();
        try {
            doctorView.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
