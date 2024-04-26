package project;

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

public class RequestManage extends Application {
	GridPane gridPane;
    private ObservableList<NewRequest> reqservice = FXCollections.observableArrayList();
    private ListView<NewRequest> listView = new ListView<>();
   
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String HOST = "localhost";
    private final int PORT = 3306;
    private final String DATABASE = "ServiceManagement";
    private final String DBUSER = "root";
    private final String DBPASS = "41561011p";
    private final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private final String SELECT_QUERY = "SELECT * FROM servicereq WHERE status = ?";
    private final String UPDATE_QUERY = "UPDATE servicereq SET Status = ? WHERE reqid=?";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        Label titleLabel = new Label("Service Requests");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button refreshButton = new Button("Back");
        refreshButton.setOnAction((event) ->{ 
        	primaryStage.close();});

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, listView, refreshButton);

        root.setCenter(vbox);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Service Requests");
        primaryStage.show();
        displayServices();
    }

    public void displayServices() {
        reqservice.clear();
        try {
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
            PreparedStatement ptmt = conn.prepareStatement(SELECT_QUERY);
            ptmt.setString(1, "Pending");
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
            	int Rid = rs.getInt("reqid");
                int UiD = rs.getInt("uid");
                String Location = rs.getString("location");
                String Service = rs.getString("service");
                String Description = rs.getString("description");
                String Status = rs.getString("status");
                
                reqservice.add(new NewRequest(Rid, UiD, Location, Service, Description, Status));
            }
            listView.setItems(reqservice);
            listView.setCellFactory(param -> new CustomList());
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRequestStatus(NewRequest reqservice, String status) {
        try {
            Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);
            PreparedStatement ptmt = conn.prepareStatement(UPDATE_QUERY);
            ptmt.setString(1, status);
            ptmt.setInt(2, reqservice.getReqid());
            ptmt.executeUpdate();
            conn.close();
            
            listView.getItems().remove(reqservice);
            listView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    class CustomList extends ListCell<NewRequest> {
        @Override
        protected void updateItem(NewRequest item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(5);

                gridPane.addRow(0, new Label("CustomerID ") {{ setStyle("-fx-font-weight: bold;"); }}, new Label("Location ") {{ setStyle("-fx-font-weight: bold;"); }}, new Label("Service ") {{ setStyle("-fx-font-weight: bold;"); }}, new Label("Description ") {{ setStyle("-fx-font-weight: bold;"); }}, 
                        new Label("Status ") {{ setStyle("-fx-font-weight: bold;"); }});

                gridPane.addRow(1, new Label(String.valueOf(item.getUid())), new Label(item.getLocation()), new Label(item.getService()), 
                        new Label(item.getDescription()), new Label(item.getStatus()));

                Button acceptButton = new Button("Accept");
                Button declineButton = new Button("Reject");

                acceptButton.setOnAction(e -> {
                    updateRequestStatus(item, "Accepted");
                    reqservice.remove(item); 
                });
                declineButton.setOnAction(e -> {
                    updateRequestStatus(item, "Denied");
                    reqservice.remove(item); 
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

