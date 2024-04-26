package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserRegister extends Application {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ServiceManagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "41561011p";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Customer Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label userIdLabel = new Label("User ID:");
        GridPane.setConstraints(userIdLabel, 0, 0);

        Label fullNameLabel = new Label("Full Name:");
        GridPane.setConstraints(fullNameLabel, 0, 1);

        Label addressLabel = new Label("Address:");
        GridPane.setConstraints(addressLabel, 0, 2);

        Label phoneLabel = new Label("Phone:");
        GridPane.setConstraints(phoneLabel, 0, 3);

        Label loginIdLabel = new Label("Login ID:");
        GridPane.setConstraints(loginIdLabel, 0, 4);

        Label loginPassLabel = new Label("Login Password:");
        GridPane.setConstraints(loginPassLabel, 0, 5);

        // TextFields
        TextField userIdInput = new TextField();
        userIdInput.setPromptText("Enter User ID");
        GridPane.setConstraints(userIdInput, 1, 0);

        TextField fullNameInput = new TextField();
        fullNameInput.setPromptText("Enter Full Name");
        GridPane.setConstraints(fullNameInput, 1, 1);

        TextField addressInput = new TextField();
        addressInput.setPromptText("Enter Address");
        GridPane.setConstraints(addressInput, 1, 2);

        TextField phoneInput = new TextField();
        phoneInput.setPromptText("Enter Phone Number");
        GridPane.setConstraints(phoneInput, 1, 3);

        TextField loginIdInput = new TextField();
        loginIdInput.setPromptText("Enter Login ID");
        GridPane.setConstraints(loginIdInput, 1, 4);

        PasswordField loginPassInput = new PasswordField();
        loginPassInput.setPromptText("Enter Login Password");
        GridPane.setConstraints(loginPassInput, 1, 5);

        // Register Button
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 6);

        // Back Button
        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 0, 6);

        backButton.setOnAction(e -> {
            // Close the registration page and open the login page
            primaryStage.close();
            Login login = new Login();
            login.start(new Stage());
        });

        registerButton.setOnAction(e -> {
            // Check if the input fields are empty
            if (userIdInput.getText().isEmpty() || fullNameInput.getText().isEmpty() ||
                addressInput.getText().isEmpty() || phoneInput.getText().isEmpty() ||
                loginIdInput.getText().isEmpty() || loginPassInput.getText().isEmpty()) {
                System.out.println("Please fill in all fields.");
                return; // Exit the method if any field is empty
            }
            
            try {
                // Parse user ID and phone number from text fields
                int userId = Integer.parseInt(userIdInput.getText());
                String fullName = fullNameInput.getText();
                String address = addressInput.getText();
                int phone = Integer.parseInt(phoneInput.getText());
                String loginId = loginIdInput.getText();
                String loginPass = loginPassInput.getText();
                
                // Connect to the database and insert the user data
                try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                    String sql = "INSERT INTO User (userid, fullname, address, phone, login_id, login_pass) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1, userId);
                    statement.setString(2, fullName);
                    statement.setString(3, address);
                    statement.setInt(4, phone);
                    statement.setString(5, loginId);
                    statement.setString(6, loginPass);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("User registered successfully!");
                    }
                } catch (SQLException ex) {
                    System.out.println("Error connecting to the database: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter valid integer values for User ID and Phone.");
            }
        });

        grid.getChildren().addAll(userIdLabel, fullNameLabel, addressLabel, phoneLabel, loginIdLabel, loginPassLabel,
                userIdInput, fullNameInput, addressInput, phoneInput, loginIdInput, loginPassInput, registerButton, backButton);

        Scene scene = new Scene(grid, 350, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
