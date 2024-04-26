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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class staffregister extends Application {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ServiceManagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "41561011p";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Registration");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label fullNameLabel = new Label("Full Name:");
        fullNameLabel.setTextFill(Color.web("#0076a3")); // Set label color
        fullNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set label font
        GridPane.setConstraints(fullNameLabel, 0, 0);

        Label addressLabel = new Label("Address:");
        addressLabel.setTextFill(Color.web("#0076a3")); // Set label color
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set label font
        GridPane.setConstraints(addressLabel, 0, 2);
        
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setTextFill(Color.web("#0076a3")); // Set label color
        phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set label font
        GridPane.setConstraints(phoneLabel, 0, 3);


        Label loginIdLabel = new Label("Login ID:");
        loginIdLabel.setTextFill(Color.web("#0076a3")); // Set label color
        loginIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set label font
        GridPane.setConstraints(loginIdLabel, 0, 4);

        Label loginPassLabel = new Label("Password:");
        loginPassLabel.setTextFill(Color.web("#0076a3")); // Set label color
        loginPassLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set label font
        GridPane.setConstraints(loginPassLabel, 0, 5);

        // TextFields
        TextField fullNameInput = new TextField();
        fullNameInput.setPromptText("Enter Full Name");
        GridPane.setConstraints(fullNameInput, 1, 0);

        TextField addressInput = new TextField();
        addressInput.setPromptText("Enter Address");
        GridPane.setConstraints(addressInput, 1, 2);
        
        TextField phoneInput = new TextField();
        phoneInput.setPromptText("Enter phone");
        GridPane.setConstraints(phoneInput, 1, 3);

        TextField loginIdInput = new TextField();
        loginIdInput.setPromptText("Enter Login ID");
        GridPane.setConstraints(loginIdInput, 1, 4);

        PasswordField loginPassInput = new PasswordField();
        loginPassInput.setPromptText("Enter Password");
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
            staffregister staffregister = new staffregister();
            staffregister.start(new Stage());
        });

        registerButton.setOnAction(e -> {
            String fullName = fullNameInput.getText();
            String address = addressInput.getText();
            String phone = phoneInput.getText();
            String loginId = loginIdInput.getText();
            String password = loginPassInput.getText();
            
            // Connect to the database and insert the staff data
            try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                String sql = "INSERT INTO Staff (fullname, address,phone, login_id, login_pass) VALUES (?, ?, ?, ?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, fullName);
                statement.setString(2, address);
                statement.setString(3, phone );
                statement.setString(4, loginId);
                statement.setString(5, password);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Staff registered successfully!");
                }
            } catch (SQLException ex) {
                System.out.println("Error connecting to the database: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(fullNameLabel, addressLabel,phoneLabel, loginIdLabel, loginPassLabel,
                fullNameInput, addressInput, phoneInput,loginIdInput, loginPassInput, registerButton, backButton);

        Scene scene = new Scene(grid, 350, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
