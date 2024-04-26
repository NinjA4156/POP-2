package project;

import project.Variable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RequestAppointment extends Application {
	int sid=0;
	Label lblREQID, lblDate, lblTime, lblService, lblWorkerID, lblUserID;
	TextField txtREQID, txtTime, txtService, txtWorkerID, txtUserID, txtReqDate;
	DatePicker Date;
	Button btnDisplay, btnSave;
	ComboBox cmbREQID, cmbWorkerID;
	NewRequest reqservice=null;
	@Override
	public void start(Stage primaryStage) throws Exception {
		 System.out.println(Variable.uid);
		
		lblREQID=new Label("RequestID : ");
		lblREQID.setLayoutX(10);
		lblREQID.setLayoutY(10);
		
		cmbREQID = new ComboBox();
		cmbREQID.setLayoutX(115);
		cmbREQID.setLayoutY(10);
		
		ArrayList reqids = getAllSID(); //get all requestid from database
		cmbREQID.getItems().addAll(reqids);
		
		btnDisplay=new Button("Display");
		btnDisplay.setLayoutX(220);
		btnDisplay.setLayoutY(10);
		btnDisplay.setOnAction((event) -> {
			sid=Variable.uid;
		    int reqid = cmbREQID.getSelectionModel().getSelectedItem() != null ? (int) cmbREQID.getSelectionModel().getSelectedItem() : -1;
		    if (reqid != -1) {
		        reqservice = searchRequest(reqid);
		        if (reqservice != null) {
		            txtUserID.setText(String.valueOf(reqservice.getReqid()));
		            txtService.setText(reqservice.getService()); // Set service to txtService
		        } else {
		            System.out.println("No request found.");
		        }
		    } else {
		        System.out.println("Please select a Request ID.");
		    }
		});



		lblDate=new Label("Date : ");
		lblDate.setLayoutX(10);
		lblDate.setLayoutY(40);
		
		Date = new DatePicker();
		Date.setLayoutX(115);
		Date.setLayoutY(40);
		
		lblTime=new Label("Time : ");
		lblTime.setLayoutX(10);
		lblTime.setLayoutY(70);
		
		txtTime=new TextField();
		txtTime.setLayoutX(115);
		txtTime.setLayoutY(70);
		
		lblService=new Label("Service : ");
		lblService.setLayoutX(10);
		lblService.setLayoutY(100);
		
		txtService=new TextField();
		txtService.setLayoutX(115);
		txtService.setLayoutY(100);
		
		lblUserID=new Label("UserID : ");
		lblUserID.setLayoutX(10);
		lblUserID.setLayoutY(130);
		
		txtUserID=new TextField();
		txtUserID.setLayoutX(115);
		txtUserID.setLayoutY(130);
		
		lblWorkerID=new Label("WorkerID : ");
		lblWorkerID.setLayoutX(10);
		lblWorkerID.setLayoutY(160);
		
		txtWorkerID=new TextField();
		txtWorkerID.setLayoutX(115);
		txtWorkerID.setLayoutY(160);
		
		
		btnSave=new Button("Save");
		btnSave.setLayoutX(115);
		btnSave.setLayoutY(250);
		btnSave.setOnAction((event)->{
			String ApDate = Date.getValue() !=null ? Date.getValue().toString(): "";
			String Time = txtTime.getText();
			int WorkerID=Integer.parseInt(txtWorkerID.getText());
			String Status = "Pending";
			NewAppoint reqappoint=new NewAppoint(ApDate, Time, WorkerID, Status);
			boolean result = saveData(reqappoint,sid);
			if(result==true) {
				showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment Sent");
			}
			else {
				showAlert(Alert.AlertType.ERROR, "Error", "Failed to request!");
			}
		});
		Pane pane = new Pane();
	    pane.getChildren().addAll(lblREQID, cmbREQID, btnDisplay);
	    pane.getChildren().addAll(lblDate, Date);
	    pane.getChildren().addAll(lblTime, txtTime);  // txtReqDate is missing here
	    pane.getChildren().addAll(lblService, txtService);
	    pane.getChildren().addAll(lblUserID, txtUserID);
	    pane.getChildren().addAll(lblWorkerID, txtWorkerID);
	    pane.getChildren().addAll(btnSave);	
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Appointment");
		primaryStage.setWidth(350);
		primaryStage.setHeight(375);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ArrayList<Integer> getAllSID() {
	    final String DRIVER = "com.mysql.cj.jdbc.Driver";
	    final String DBNAME = "ServiceManagement";
	    final String DBUSER = "root";
	    final String USERPASS = "41561011p";
	    final int PORT = 3306;
	    final String HOST = "localhost";
	    final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME;
	    final String sql = "SELECT * FROM servicereq where Status='Accepted';";
	    ArrayList<Integer> reqids = new ArrayList<>();
	    try {
	        Class.forName(DRIVER);
	        try (Connection conn = DriverManager.getConnection(URL, DBUSER, USERPASS);
	             Statement stat = conn.createStatement();
	             ResultSet rs = stat.executeQuery(sql)) {
	            while (rs.next()) {
	                int reqid = rs.getInt("reqid");
	                reqids.add(reqid);
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("Error : " + ex.getMessage());
	    }
	    return reqids;
	}

	public NewRequest searchRequest(int reqid) {
	    final String DRIVER = "com.mysql.cj.jdbc.Driver";
	    final String DBNAME = "ServiceManagement";
	    final String DBUSER = "root";
	    final String USERPASS = "41561011p";
	    final int PORT = 3306;
	    final String HOST = "localhost";
	    final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME;
	    final String sql = "SELECT reqid,uid,service FROM servicereq WHERE reqid=?";
	    NewRequest reqservice = null;
	    try {
	        Class.forName(DRIVER); // Loading driver
	        try (Connection conn = DriverManager.getConnection(URL, DBUSER, USERPASS);
	             PreparedStatement pstat = conn.prepareStatement(sql)) {
	            pstat.setInt(1, reqid);
	            try (ResultSet rs = pstat.executeQuery()) {
	                while (rs.next()) {
	                    int Reqid = rs.getInt("reqid");
	                    int Userid = rs.getInt("uid");
	                    String Service = rs.getString("service");
	                    reqservice = new NewRequest(Reqid, Userid, Service);
	                }
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("Error : " + ex.getMessage());
	    }
	    return reqservice;
	}



	public boolean saveData(NewAppoint reqappoint, int sid) {
	    final String DRIVER = "com.mysql.cj.jdbc.Driver";
	    final String DBNAME = "ServiceManagement";
	    final String DBUSER = "root";
	    final String USERPASS = "41561011p";
	    final int PORT = 3306;
	    final String HOST = "localhost";
	    final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME;
	    final String sql = "INSERT INTO appointment (StaffID, ADate, ATime, Service, UserID, WorkerID, AStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    boolean result = false;

	    try {
	        Class.forName(DRIVER);
	        try (Connection conn = DriverManager.getConnection(URL, DBUSER, USERPASS);
	             PreparedStatement stat = conn.prepareStatement(sql)) {

	        	 stat.setInt(1, sid);
	        	 System.out.println(Variable.uid);
	        	 stat.setString(2, reqappoint.getDate());
	            stat.setString(3, reqappoint.getTime());
	            stat.setString(4, reqservice.getService());
	            stat.setInt(5, reqservice.getUid());
	            stat.setInt(6, reqappoint.getWid());
	            stat.setString(7, reqappoint.getStatus());

	            stat.executeUpdate(); // Insert record
	            result = true;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("Error : " + ex.getMessage());
	    }
	    return result;
	}
	private void showAlert(Alert.AlertType type, String title, String message) {
	    Alert alert = new Alert(type);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
