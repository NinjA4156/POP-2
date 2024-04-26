package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Worker extends Application{	
	
	public static void main(String[] args) {
		//System.out.println("Hello");
		launch(args);//call start()
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	    Label lblFullName = new Label("Full Name : ");
	    Label lblAddress = new Label("Address : ");
	    Label lblContact = new Label("Contact : ");
	    Label lblExpertise = new Label("Expertise:");

	    TextField txtFullName = new TextField();
	    TextArea txtAddress = new TextArea();
	    TextField txtContact = new TextField();

	    ComboBox<String> cmbExpertise = new ComboBox<>();
	    cmbExpertise.getItems().addAll(
	            "Plumber",
	            "Cleaner",
	            "Electrician"
	    );
	    cmbExpertise.setValue("Cleaner"); // Set default selection

	    Button btnSave=new Button("Save");
	    btnSave.setOnAction((event)->{
	        String Fullname=txtFullName.getText();
	        String Address=txtAddress.getText();
	        String Contact=txtContact.getText();
	        String Expertise=cmbExpertise.getValue();

	        NewWorker person=new NewWorker(Fullname, Address, Contact, Expertise);
	        boolean res = saveRecord(person); //call method
	        if(res==true) {
	        	showAlert(Alert.AlertType.INFORMATION, "Success", "Sevice Registered");
	        }
	        else {
	        	showAlert(Alert.AlertType.ERROR, "Error", "Failed to Register");
	        }
	    });

	    Button btnClose=new Button("Close");
	    btnClose.setOnAction((event)->{
	        primaryStage.close(); //close the window
	    });

	    GridPane pane = new GridPane();
	    pane.setHgap(10);
	    pane.setVgap(10);

	    // Arrange labels and text fields in the grid pane
	    pane.add(lblFullName, 0, 0);
	    pane.add(txtFullName, 1, 0);
	    pane.add(lblAddress, 0, 1);
	    pane.add(txtAddress, 1, 1);
	    pane.add(lblContact, 0, 2);
	    pane.add(txtContact, 1, 2);
	    pane.add(lblExpertise, 0, 3);
	    pane.add(cmbExpertise, 1, 3);
	    pane.add(btnSave, 0, 4);
	    pane.add(btnClose, 1, 4);

	    Scene scene = new Scene(pane);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Insert New Person");
	    primaryStage.setWidth(650);
	    primaryStage.setHeight(450);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}

	public boolean saveRecord(NewWorker worker) {
		
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="ServiceManagement";
		String DBUSER="root";
		String DBPASS="41561011p";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="INSERT INTO worker(fullname, address, contact, expertise) VALUES(?, ?, ?, ?)";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, worker.getFullname());
			pstat.setString(2, worker.getAddress());
			pstat.setString(3, worker.getContact());
			pstat.setString(4, worker.getExpertise());
			
			pstat.executeUpdate();//Insert Record
			pstat.close();
			conn.close();
			result=true;
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
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