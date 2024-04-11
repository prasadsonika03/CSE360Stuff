package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.ArrayList;


public class DoctorView extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Text message = new Text("Welcome to the Doctor's View!");
        root.getChildren().add(message);
        VBox mainBox = new VBox();
        TabPane patientsTabPane = new TabPane();
        TextField dateField = new TextField();
        Label bpField = new Label();
        TextField allergyField = new TextField();
        Label heightField = new Label();
        Label weightField = new Label();
        Label bodyTempField = new Label();
        TextField concernsField = new TextField();
        List<String> fileNames = new ArrayList<>();
        File[] files = new File(".").listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().matches("\\w+_\\w+_[0-9]{6}\\.txt")) {
                fileNames.add(file.getName());
            }
        }

        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                String firstName = "";
                String lastName = "";
                String dob = "";
                String phoneNumber = "";
                String guardianName = "";
                String visitInfo = "";

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        switch (key) {
                            case "First Name":
                                firstName = value;
                                break;
                            case "Last Name":
                                lastName = value;
                                break;
                            case "Date of Birth":
                                dob = value;
                                break;
                            case "Phone Number":
                                phoneNumber = value;
                                break;
                            case "Guardian Name":
                                guardianName = value;
                                break;
                            case "Visits":
                                visitInfo = value;
                                while ((line = reader.readLine()) != null) {
                                    visitInfo = visitInfo + "\n" + line;
                                }
                                break;
                        }
                    }
                }

                // Create a new tab for the patient
                Tab patientTab = new Tab(firstName + " " + lastName);
                VBox patientInfoBox = new VBox(); // You can use any layout container

                // Add patient-specific content to the tab
                patientInfoBox.getChildren().addAll(
                        new Label("First Name: " + firstName),
                        new Label("Last Name: " + lastName),
                        new Label("Date of Birth: " + dob),
                        new Label("Phone Number: " + phoneNumber),
                        new Label("Guardian Name: " + guardianName),
                        new Label("\nPatient History: \n" + visitInfo)
                );

                patientsTabPane.getTabs().add(patientTab);
                patientTab.setContent(patientInfoBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ScrollPane scrollPane = new ScrollPane(patientsTabPane);
        scrollPane.setFitToWidth(true);

        mainBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        mainBox.setAlignment(Pos.TOP_CENTER); // Align at the top center
        mainBox.setSpacing(20);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(scrollPane, mainBox);
        splitPane.setDividerPositions(0.30);

        VBox textFieldsBox = new VBox();
        textFieldsBox.setSpacing(20); // Increase spacing between text fields

        // Create and configure labels and text fields
        Label dateLabel = new Label("Date:");
        HBox dateBox = new HBox(dateLabel, dateField);
        dateBox.setSpacing(10);


        HBox weightHeightBox = new HBox(new Label("Weight:"), weightField, new Label("Height:"), heightField);
        weightHeightBox.setSpacing(10);
        weightHeightBox.setAlignment(Pos.CENTER_LEFT);
        weightHeightBox.setSpacing(10);
        




        HBox bodyTempBpBox = new HBox(new Label("Body Temperature:"), bodyTempField, new Label("Blood Pressure:"), bpField);
        bodyTempBpBox.setSpacing(10);
        bodyTempBpBox.setAlignment(Pos.CENTER_LEFT);

        // Create VBox for Known Allergies
        VBox allergyVBox = new VBox();
        Label allergyLabel = new Label("Observations:");
        allergyField.setPrefHeight(100); // Increase the height of the text field
        allergyVBox.getChildren().addAll(allergyLabel, allergyField);

        // Create VBox for Health Concerns
        VBox concernsVBox = new VBox();
        Label concernsLabel = new Label("Prescribed Medication");
        concernsField.setPrefHeight(100); // Increase the height of the text field
        concernsVBox.getChildren().addAll(concernsLabel, concernsField);

        // Add boxes to the VBox
        textFieldsBox.getChildren().addAll(dateBox, weightHeightBox, bodyTempBpBox, allergyVBox, concernsVBox);

        // Create the DoctorView heading
        Text heading = new Text("Doctor View");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        heading.setFill(Color.WHITE);
        StackPane header = new StackPane(heading);
        header.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        header.setPadding(new Insets(10));

        // Add the header directly to the mainLayout without spacing
        mainBox.getChildren().add(header);

        mainBox.getChildren().add(textFieldsBox);

        Button submitButton = createButton("Submit");
        Button logoutButton = createButton("Logout");
        Button callButton = createButton("Call");
        
        

        submitButton.setPrefSize(150, 50);
        logoutButton.setPrefSize(150, 50);
        callButton.setPrefSize(150, 50);

        dateField.setPrefSize(150, 50);
        bpField.setPrefSize(150, 50);
        allergyField.setPrefSize(150, 50);
        heightField.setPrefSize(150, 50);
        weightField.setPrefSize(150, 50);
        bodyTempField.setPrefSize(150, 50);
        concernsField.setPrefSize(150, 50);

        submitButton.setOnAction(e -> {
            String height = heightField.getText();
            String weight = weightField.getText();
            String bodyTemp = bodyTempField.getText();
            String date = dateField.getText();
            String bp = bpField.getText();
            String allergy = allergyField.getText();
            String concerns = concernsField.getText();
            if (fieldsAreFilled( dateField,  allergyField, concernsField)) {
                Tab selectedTab = patientsTabPane.getSelectionModel().getSelectedItem();
                VBox contentBox = (VBox) selectedTab.getContent();
                String tabContent = "";
                for (Node node : contentBox.getChildren()) {
                    if (node instanceof Label) {
                        tabContent += ((Label) node).getText() + "\n";
                    }
                }

                String firstName = extractValue(tabContent, "First Name:");
                String lastName = extractValue(tabContent, "Last Name:");
                String dob = extractValue(tabContent, "Date of Birth:");
                String fileName = firstName + "_" + lastName + "_" + dob + ".txt";
                if (writeDataToFile(fileName, date, weight, height, bodyTemp, bp, allergy, concerns)) {
                    showSuccessMessage("Patient data updated!");
                } else {
                    showError("Failed to update patient info. Please try again.");
                }
            } else {
                showError("Please fill in all fields.");
            }
            primaryStage.close();
            new DoctorView().start(new Stage());
        });
        
        callButton.setOnAction(e -> {
            Tab selectedTab = patientsTabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                String patientName = selectedTab.getText();
                showInfoMessage(patientName + " has been notified");
            }
        });

        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new login_page().start(new Stage());
        });

        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(callButton, submitButton, logoutButton);
        buttonsBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().add(buttonsBox);

        Scene scene = new Scene(splitPane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Doctor's View");
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

    private String extractValue(String content, String key) {
        int startIndex = content.indexOf(key);
        if (startIndex != -1) {
            int endIndex = content.indexOf("\n", startIndex);
            if (endIndex != -1) {
                return content.substring(startIndex + key.length(), endIndex).trim();
            }
        }
        return "";
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfoMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Call Patient");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private boolean writeDataToFile(String fileName, String date, String weight, String height, String bodyTemp, String bp, String allergies, String concerns) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(".\n");
            writer.write("Date = " + date + "\n");
            writer.write("Height = " + height + "\n");
            writer.write("Weight = " + weight + "\n");
            writer.write("Body Temp = " + bodyTemp + "\n");
            writer.write("BP = " + bp + "\n");
            writer.write("allergies = " + allergies + "\n");
            writer.write("concerns = " + concerns + "\n");
            writer.close();
            System.out.println("Data appended to file '" + fileName + "' successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while writing data to file.");
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
