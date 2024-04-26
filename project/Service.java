package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Service extends Application {
	

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    Label lblSname = new Label("Service Name: ");
	    Label lblSdesc = new Label("Service Description: ");
	    Label lblDuration = new Label("Duration (minutes): ");
	    Label lblCost = new Label("Cost ($): ");

	    TextField txtSname = new TextField();
	    TextArea txtSdesc = new TextArea();
	    TextField txtDuration = new TextField();
	    TextField txtCost = new TextField();

	    Button btnCreate = new Button("Register");
	    btnCreate.setOnAction((event) -> {
	        String Name = txtSname.getText();
	        String Description = txtSdesc.getText();
	        String Duration = txtDuration.getText();
	        int Cost = Integer.parseInt(txtCost.getText());

	        NewService service = new NewService(Name, Description, Duration, Cost);
	        boolean res = saveData(service);
	        if (res == true) {
	            showAlert(Alert.AlertType.INFORMATION, "Success", "Service Registered");
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Register");
	        }
	    });

	    Button btnClose = new Button("Back");
	    btnClose.setOnAction((event) -> {
	        primaryStage.close();
	    });

	    GridPane pane = new GridPane();
	    pane.setHgap(10);
	    pane.setVgap(10);

	    // Add labels and text fields to the grid pane
	    pane.addRow(0, lblSname, txtSname);
	    pane.addRow(1, lblSdesc, txtSdesc);
	    pane.addRow(2, lblDuration, txtDuration);
	    pane.addRow(3, lblCost, txtCost);
	    pane.addRow(4, btnCreate, btnClose);

	    Scene scene = new Scene(pane);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Service Register");
	    primaryStage.setWidth(650);
	    primaryStage.setHeight(450);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}

	
	public boolean saveData(NewService service) {
		
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="ServiceManagement";
		String DBUSER="root";
		String DBPASS="41561011p";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="INSERT INTO service(srname,sdescription,sduration,scost) VALUES(?, ?, ?, ?)";
		try {
			Class.forName(DRIVER);
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//to establish connection with database
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, service.getName());
			pstat.setString(2, service.getDescription());
			pstat.setString(3, service.getDuration());
			pstat.setInt(4, service.getCost());
			pstat.executeUpdate();
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

