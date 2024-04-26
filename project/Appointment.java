package project;

import project.Variable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment extends Application {
    private ObservableList<NewAppoint> reqapp = FXCollections.observableArrayList();
    private ListView<NewAppoint> listView = new ListView<>();
    
    private final String HOST = "localhost";
    private final int PORT = 3306;
    private final String DATABASE = "ServiceManagement";
    private final String DBUSER = "root";
    private final String DBPASS = "41561011p";
    private final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private final String SELECT_QUERY = "SELECT * FROM appointment WHERE AStatus = ? AND UserID=?";
    private final String UPDATE_QUERY = "UPDATE appointment SET AStatus = ? WHERE AID=?";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Appointment Requests");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button refreshButton = new Button("Back");
        refreshButton.setOnAction((event) -> primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, listView, refreshButton);

        root.setCenter(vbox);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Appointment Requests");
        primaryStage.show();
        int userID =Variable.uid; // Renamed to follow Java naming conventions
        displayAppointment(userID);
    }

    public void displayAppointment(int userID) {
        reqapp.clear();
        try (Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
             PreparedStatement ptmt = conn.prepareStatement(SELECT_QUERY)) {
            ptmt.setString(1, "Pending");
            ptmt.setInt(2, userID);
            try (ResultSet rs = ptmt.executeQuery()) {
                while (rs.next()) {
                    int apID = rs.getInt("AID");
                    int staffID = rs.getInt("StaffID");
                    int workerID = rs.getInt("WorkerID");
                    String date = rs.getString("ADate");
                    String time = rs.getString("ATime");
                    String service = rs.getString("Service");

                    // Add null checks
                    if (date != null && time != null && service != null) {
                        reqapp.add(new NewAppoint(apID, staffID, workerID, date, time, service));
                    } else {
                        // Handle null values
                    }
                }
            }
            listView.setItems(reqapp);
            listView.setCellFactory(param -> new CustomListCell());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRequestStatus(NewAppoint reqapp, String status) {
        try (Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
             PreparedStatement ptmt = conn.prepareStatement(UPDATE_QUERY)) {
            ptmt.setString(1, status);
            ptmt.setInt(2, reqapp.getAid());
            ptmt.executeUpdate();
            listView.getItems().remove(reqapp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class CustomListCell extends ListCell<NewAppoint> {
        @Override
        protected void updateItem(NewAppoint item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(5);

                gridPane.addRow(0, new Label("Appointment ID "), new Label("StaffID "), new Label("Appointment Date "),
                        new Label("Appointment Time "), new Label("Service "), new Label("Worker ID "));

                gridPane.addRow(1, new Label(String.valueOf(item.getAid())), new Label(String.valueOf(item.getSid())),
                        new Label(item.getDate()), new Label(item.getTime()), new Label(item.getService()),
                        new Label(String.valueOf(item.getWid())));

                Button acceptButton = new Button("Accept");
                Button declineButton = new Button("Decline");

                acceptButton.setOnAction(e -> {
                    updateRequestStatus(item, "Accepted");
                    reqapp.remove(item);
                });
                declineButton.setOnAction(e -> {
                    updateRequestStatus(item, "Denied");
                    reqapp.remove(item);
                });

                HBox buttonBox = new HBox(10);
                buttonBox.getChildren().addAll(acceptButton, declineButton);

                VBox vbox = new VBox(5);
                vbox.getChildren().addAll(gridPane, buttonBox);

                setGraphic(vbox);
            }
        }
    }
}
