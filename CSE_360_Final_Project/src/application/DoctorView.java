package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.*;
import javafx.scene.text.Font;

public class DoctorView extends Application {

    private static final String PATIENTS_FOLDER = "patients"; // Folder to store patient files

    @Override
    public void start(Stage primaryStage) {
        // Ensure patient directory exists
        try {
            Files.createDirectories(Paths.get(PATIENTS_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create demo patient file
        createDemoPatientFile("Mary_Gleason_14-05-2003.txt", "Mary", "Gleason", "14-05-2003", "Mary's past medical history.");

        ListView<String> patientList = new ListView<>();
        TextArea observationsArea = new TextArea();
        TextArea prescribedMedicationArea = new TextArea();

        patientList.getItems().addAll("Mary Gleason 14-05-2003", "Bob Johnson 12-02-1987", "Chris Buelt 17-11-1989");

        VBox patientDetailsBox = createPatientDetailsBox(observationsArea, prescribedMedicationArea);

        // Buttons
        Button callButton = new Button("Call");
        Button patientHistoryButton = new Button("Patient History");
        Button submitButton = new Button("Submit");
        Button logoutButton = new Button("Logout");

        setButtonActions(primaryStage, patientList, observationsArea, prescribedMedicationArea, callButton, patientHistoryButton, submitButton);

        logoutButton.setOnAction(e -> primaryStage.close());

        HBox buttonsBox = new HBox(10, callButton, patientHistoryButton, submitButton, logoutButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(10));

        VBox mainLayout = new VBox(new HBox(new VBox(patientList, callButton), patientDetailsBox), buttonsBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Doctor's View");
        primaryStage.show();
    }

    private VBox createPatientDetailsBox(TextArea observationsArea, TextArea prescribedMedicationArea) {
        VBox patientDetailsBox = new VBox();
        patientDetailsBox.setPadding(new Insets(10));
        patientDetailsBox.setSpacing(8);

        Label patientVitalsLabel = new Label("Patient Vitals:");
        patientVitalsLabel.setFont(new Font("Arial", 16));

        HBox vitalsBox = new HBox();
        vitalsBox.setSpacing(10);
        TextField weightField = new TextField("130 lbs");
        TextField heightField = new TextField("5'5 ft");
        TextField bodyTempField = new TextField("97 F");
        TextField bpField = new TextField("120/80");
        vitalsBox.getChildren().addAll(new Label("Weight:"), weightField, new Label("Height:"), heightField, new Label("Body Temp:"), bodyTempField, new Label("BP:"), bpField);

        patientDetailsBox.getChildren().addAll(patientVitalsLabel, vitalsBox, new Label("Observations:"), observationsArea, new Label("Prescribed Medication:"), prescribedMedicationArea);
        return patientDetailsBox;
    }

    private void setButtonActions(Stage primaryStage, ListView<String> patientList, TextArea observationsArea, TextArea prescribedMedicationArea, Button callButton, Button patientHistoryButton, Button submitButton) {
        callButton.setOnAction(e -> {
            String selectedPatient = patientList.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                showAlert(Alert.AlertType.INFORMATION, "Calling", selectedPatient + " has been called.");
            }
        });

        patientHistoryButton.setOnAction(e -> {
            String selectedPatient = patientList.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                String patientFileName = PATIENTS_FOLDER + "/" + selectedPatient.replace(" ", "_") + ".txt";
                displayPatientHistory(patientFileName);
            }
        });

        submitButton.setOnAction(e -> {
            String selectedPatient = patientList.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                String patientFileName = PATIENTS_FOLDER + "/" + selectedPatient.replace(" ", "_") + ".txt";
                savePatientDetails(patientFileName, observationsArea.getText(), prescribedMedicationArea.getText());
                showAlert(Alert.AlertType.INFORMATION, "Information", "Details saved for " + selectedPatient);
            }
        });
    }

    private void displayPatientHistory(String fileName) {
        String content = readFileContent(fileName);
        if (content.isEmpty()) {
            content = "No history available.";
        }
        showAlert(Alert.AlertType.INFORMATION, "Patient History", content);
    }

    private void savePatientDetails(String fileName, String observations, String prescribedMedication) {
        try {
            FileWriter writer = new FileWriter(fileName, true); // Set true for append mode
            writer.write("Observations: " + observations + "\n");
            writer.write("Prescribed Medication: " + prescribedMedication + "\n");
            writer.write("---\n"); // Separator for different entries
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save patient details.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String readFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void createDemoPatientFile(String filename, String firstName, String lastName, String dob, String history) {
        Path filePath = Paths.get(PATIENTS_FOLDER, filename);
        if (!Files.exists(filePath)) {
            try {
                Files.writeString(filePath, "First Name: " + firstName + "\nLast Name: " + lastName + "\nDOB: " + dob + "\nHistory:\n" + history + "\n---\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
