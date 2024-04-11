package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

public class patient_view extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        mainLayout.setAlignment(Pos.TOP_CENTER); // Align at the top center
        mainLayout.setSpacing(20);
        mainLayout.setPadding(new Insets(20));

        Text heading = new Text("Patient View");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        heading.setFill(Color.WHITE);
        StackPane header = new StackPane(heading);
        header.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        header.setPadding(new Insets(10));
        mainLayout.getChildren().add(header);

        Button bookAppointmentButton = createButton("Book an Appointment");
        Button editProfileButton = createButton("Edit/View Profile");
        Button visitSummaryButton = createButton("Visit Summary");
        Button reportsButton = createButton("Reports");
        Button callButton = createButton("Call");

        VBox sidebar = new VBox();
        sidebar.setSpacing(10);
        sidebar.setAlignment(Pos.CENTER_LEFT);
        sidebar.getChildren().addAll(
                bookAppointmentButton,
                editProfileButton,
                visitSummaryButton,
                reportsButton,
                callButton
        );

        mainLayout.getChildren().add(sidebar);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Patient View");
        primaryStage.show();

        // Button actions
        bookAppointmentButton.setOnAction(e -> bookAppointment(primaryStage));
        editProfileButton.setOnAction(e -> editProfile());
        visitSummaryButton.setOnAction(e -> showVisitSummary());
        reportsButton.setOnAction(e -> showReports());
        callButton.setOnAction(e -> makeCall());
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        button.setPrefWidth(200);
        return button;
    }

    private void bookAppointment(Stage primaryStage) {
        // Logic for booking an appointment goes here
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Booking");
        alert.setHeaderText(null);
        alert.setContentText("Your appointment is booked and the clinic has been notified.");
        alert.showAndWait();
    }

    private void editProfile() {
        String fileName = "firstname_lastname_dob.txt";
        String[] profileData = readProfileData(fileName);

        if (profileData != null && profileData.length == 5) {
            String firstName = profileData[0];
            String lastName = profileData[1];
            String dob = profileData[2];
            String phoneNumber = profileData[3];
            String guardianName = profileData[4];

            // Displaying the profile data
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profile Information");
            alert.setHeaderText(null);
            alert.setContentText(
                    "First Name: " + firstName + "\n" +
                    "Last Name: " + lastName + "\n" +
                    "Date of Birth: " + dob + "\n" +
                    "Phone Number: " + phoneNumber + "\n" +
                    "Guardian Name: " + guardianName
            );
            alert.showAndWait();

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid profile data.");
        }
    }

    private String[] readProfileData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String[] profileData = new String[5];
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch (key) {
                        case "First Name":
                            profileData[0] = value;
                            break;
                        case "Last Name":
                            profileData[1] = value;
                            break;
                        case "Date of Birth":
                            profileData[2] = value;
                            break;
                        case "Phone Number":
                            profileData[3] = value;
                            break;
                        case "Guardian Name":
                            profileData[4] = value;
                            break;
                    }
                }
            }
            return profileData;
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while reading the profile data.");
            return null;
        }
    }

    private void showVisitSummary() {
        // Logic for displaying visit summary goes here
        // Read data from the patient's visit history
        // Display the complete report including the date of the most current visit, doctor's name,
        // observations, and prescribed medications
    }

    private void showReports() {
        // Logic for showing reports goes here
        // Show all visits made to the clinic based on the selected date
    }

    private void makeCall() {
        // Logic for making a call goes here
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Call");
        alert.setHeaderText(null);
        alert.setContentText("The clinic has been notified and will get back to you on your respective cell.");
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
