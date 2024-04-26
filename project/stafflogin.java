package project;

import project.Variable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class stafflogin extends Application {

	int userid=0;
	
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ServiceManagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "41561011p";

    @Override
    public void start(Stage primaryStage) {
    	
    	primaryStage.setTitle("Staff Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label loginIdLabel = new Label("Login ID:");
        GridPane.setConstraints(loginIdLabel, 0, 0);

        Label loginPassLabel = new Label("Password:");
        GridPane.setConstraints(loginPassLabel, 0, 1);

        // TextFields
        TextField loginIdInput = new TextField();
        loginIdInput.setPromptText("Enter Login ID");
        GridPane.setConstraints(loginIdInput, 1, 0);

        PasswordField loginPassInput = new PasswordField();
        loginPassInput.setPromptText("Enter Password");
        GridPane.setConstraints(loginPassInput, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            String loginId = loginIdInput.getText();
            String password = loginPassInput.getText();
            
            // Connect to the database and check if login credentials are valid
            try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                String sql = "SELECT * FROM Staff WHERE login_id = ? AND login_pass = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, loginId);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                	userid=resultSet.getInt("staffid");
                	Variable.uid=userid;
                	System.out.println(userid);
                    System.out.println("Login successful!");
                    
                    Stage stage=new Stage();
                    StaffDashboard staff=new StaffDashboard();
                    staff.start(stage);
                
                    
                } else {
                    System.out.println("Invalid login credentials.");
                }
            } catch (SQLException ex) {
                System.out.println("Error connecting to the database: " + ex.getMessage());
            }
        });

        grid.getChildren().addAll(loginIdLabel, loginPassLabel, loginIdInput, loginPassInput, loginButton);

        Scene scene = new Scene(grid, 350, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
