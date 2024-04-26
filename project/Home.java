package project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Home extends Application {
    @Override
    public void start(Stage primaryStage) {

        Label lblst = new Label("Welcome To Service Management System");

        Label choose = new Label("!Click from below button to choose User!");
        Button btnuser = new Button("Login Customer");
        btnuser.setOnAction((event) -> {
            Stage userstage = new Stage();
            Login user = new Login();
            user.start(userstage);
        });

        Button btnstaff = new Button("Login Staff");
        btnstaff.setOnAction((event) -> {
            Stage staffstage = new Stage();
            stafflogin staff = new stafflogin();
            staff.start(staffstage);
        });

        GridPane root = new GridPane();

        GridPane.setConstraints(lblst, 0, 0);
        GridPane.setConstraints(choose, 0, 1);
        GridPane.setConstraints(btnuser, 0, 2);
        GridPane.setConstraints(btnstaff, 0, 3);

        root.getChildren().addAll(lblst, choose, btnuser, btnstaff);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Service Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
