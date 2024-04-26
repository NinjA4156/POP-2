package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Dashboard");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Manage Account Button
        Button manageAccountButton = new Button("Manage Account");
        GridPane.setConstraints(manageAccountButton, 0, 0);

        // Request Service Button
        Button requestServiceButton = new Button("Request Service");
        GridPane.setConstraints(requestServiceButton, 0, 1);
        requestServiceButton.setOnAction((event)->{
        	Stage st=new Stage();
        	RequestService sr=new RequestService();
        	try {
				sr.start(st);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

        // Appointment Status Button
        Button appointmentStatusButton = new Button("Appointment");
        GridPane.setConstraints(appointmentStatusButton, 0, 2);
        appointmentStatusButton.setOnAction((event)->{
        	Stage st=new Stage();
        	Appointment sr=new Appointment();
        	try {
				sr.start(st);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

        // Request Status Button
        Button viewServiceButton = new Button("View Service");
        GridPane.setConstraints(viewServiceButton, 0, 3);
        viewServiceButton.setOnAction((event)->{
        	Stage st=new Stage();
        	ViewService sr=new ViewService();
        	try {
				sr.start(st);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });


        // Logout Button
        Button logoutButton = new Button("Logout");
        GridPane.setConstraints(logoutButton, 0, 4);

        // Event handler for the logout button
        logoutButton.setOnAction(e -> {
            primaryStage.close(); // Close the user dashboard window
            stafflogin staffLogin = new stafflogin();
            staffLogin.start(new Stage()); // Open the staff login window
        });

        grid.getChildren().addAll(manageAccountButton, requestServiceButton, appointmentStatusButton, viewServiceButton, logoutButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
