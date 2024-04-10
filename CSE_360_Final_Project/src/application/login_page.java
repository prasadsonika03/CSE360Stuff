package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class login_page extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main VBox for the layout
        VBox mainLayout = new VBox();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        mainLayout.setAlignment(Pos.TOP_CENTER); // Align at the top center
        mainLayout.setSpacing(20);

        // Create the login heading
        Text heading = new Text("Log In");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        heading.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(heading);
        headerPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        headerPane.setPadding(new Insets(10));

        // Add the headerPane directly to the mainLayout without spacing
        mainLayout.getChildren().add(headerPane);

        // Add company logo
        //ImageView logoImageView = new ImageView(new Image(getClass().getResourceAsStream("clinic_logo.png")));
        //logoImageView.setFitWidth(200); // Set width of the logo
        //logoImageView.setPreserveRatio(true); // Preserve aspect ratio of the logo

        //StackPane logoPane = new StackPane(logoImageView);
        //logoPane.setAlignment(Pos.CENTER);
        //mainLayout.getChildren().add(logoPane);

        // Create the buttons
        Button patientPortalBtn = createButton("Patient Portal");
        Button employeePortalBtn = createButton("Employee Portal");
        Button patientsignupBtn = createButton("Patient Signup");

        // Set larger size for buttons
        patientPortalBtn.setPrefSize(150, 50);
        employeePortalBtn.setPrefSize(150, 50);
        patientsignupBtn.setPrefSize(150, 50);

        // Add actions to buttons
        patientPortalBtn.setOnAction(e -> {
        	primaryStage.close();
            new patient_portal_login().start(new Stage());
            System.out.println("Redirecting to patient_portal_login.java"); 
        });

        employeePortalBtn.setOnAction(e -> {
        	primaryStage.close();
            new employee_portal().start(new Stage());
            System.out.println("Redirecting to employee_portal.java");
        });

        patientsignupBtn.setOnAction(e -> {
        	primaryStage.close();
            new patient_signup().start(new Stage());
            System.out.println("Redirecting to patient_signup.java");
        });

        // Add buttons to an HBox and add HBox to main layout
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(patientPortalBtn, employeePortalBtn, patientsignupBtn);
        buttonsBox.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(buttonsBox);

        // Create the scene
        Scene scene = new Scene(mainLayout, 400, 400); // Increased height to accommodate the logo
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital Login Page");
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
