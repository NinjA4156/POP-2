package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StaffDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons for various functionalities
        Button requestAppointmentBtn = new Button("Request Appointment");
        Button serviceRequestBtn = new Button("Service Request");
        Button addWorkerBtn = new Button("Add Worker");
        Button viewWorkerBtn = new Button("View Worker");
        Button addServiceBtn = new Button("Add Service");
        Button serviceListBtn = new Button("Service List");

        // Set action handlers for each button
        requestAppointmentBtn.setOnAction(e -> {
        	Stage stage=new Stage();
            RequestAppointment sent=new RequestAppointment();
            try {
				sent.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        serviceRequestBtn.setOnAction(e -> {
            Stage stage=new Stage();
            RequestManage manage=new RequestManage();
            try {
				manage.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        addWorkerBtn.setOnAction(e -> {
        	Stage stage=new Stage();
            Worker add=new Worker();
            try {
				add.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        viewWorkerBtn.setOnAction(e -> {
        	Stage stage=new Stage();
            ViewWorker see=new ViewWorker();
            try {
				see.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        addServiceBtn.setOnAction(e -> {
        	Stage stage=new Stage();
            Service add=new Service();
            try {
				add.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        serviceListBtn.setOnAction(e -> {
        	Stage stage=new Stage();
            ViewService see=new ViewService();
            try {
				see.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.close());

        // Create a layout for the dashboard
        VBox dashboardLayout = new VBox(10);
        dashboardLayout.setPadding(new Insets(20));
        dashboardLayout.getChildren().addAll(
                requestAppointmentBtn,
                serviceRequestBtn,
                addWorkerBtn,
                viewWorkerBtn,
                addServiceBtn,
                serviceListBtn,
                backButton
        );

        // Set up the scene and display the stage
        Scene scene = new Scene(dashboardLayout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Staff Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
