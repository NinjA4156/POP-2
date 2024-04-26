package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceRequest extends Application {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ServiceManagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "41561011p"; // Replace with your MySQL password

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Service Request");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Service ComboBox
        Label serviceLabel = new Label("Service:");
        GridPane.setConstraints(serviceLabel, 0, 0);
        ComboBox<String> serviceComboBox = new ComboBox<>();
        serviceComboBox.getItems().addAll("Cleaner", "Painter", "Plumber", "Electrician");
        serviceComboBox.setPromptText("Select a service");
        GridPane.setConstraints(serviceComboBox, 1, 0);

        // Description Field
        Label descriptionLabel = new Label("Description:");
        GridPane.setConstraints(descriptionLabel, 0, 1);
        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPromptText("Enter description");
        GridPane.setConstraints(descriptionTextArea, 1, 1);

        // Location Field
        Label locationLabel = new Label("Location:");
        GridPane.setConstraints(locationLabel, 0, 2);
        TextField locationField = new TextField();
        locationField.setPromptText("Enter location");
        GridPane.setConstraints(locationField, 1, 2);

        // Submit Button
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 3);

        // Event handler for the submit button
        submitButton.setOnAction(e -> {
            String service = serviceComboBox.getValue();
            String description = descriptionTextArea.getText();
            String location = locationField.getText();

            // Connect to the database and insert the service request
            try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                String sql = "INSERT INTO ServiceReq (uid, service, Description, Location, status) VALUES (?, ?, ?, ?, 'Pending')";
                PreparedStatement statement = conn.prepareStatement(sql);
                // Set parameters for the SQL query
                statement.setInt(1, 1); // Replace 1 with the actual user ID
                statement.setString(2, service);
                statement.setString(3, description);
                statement.setString(4, location);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Service request submitted successfully!");
                }
            } catch (SQLException ex) {
                System.out.println("Error connecting to the database: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(serviceLabel, serviceComboBox, descriptionLabel, descriptionTextArea,
                locationLabel, locationField, submitButton);

        Scene scene = new Scene(grid, 700, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
