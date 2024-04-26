package project;
import project.Variable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RequestService extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	
	ComboBox<String> Service;
	int usid=Variable.uid;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Label lblLocation = new Label("Location : ");
		Label lblService = new Label("Service : ");
		Label lblDescription = new Label("Description : ");
		
		
		TextField txtLocation = new TextField();
		
		Service=new ComboBox<>();
		Service.setItems(FXCollections.observableArrayList("Plumber","Electrician","Cleaner"));
		Service.setValue("Cleaner");
		
		TextArea txtDescription = new TextArea();
		
		
		Button btnrequest=new Button("Book");
		btnrequest.setOnAction((event)->{
			
			
			int Usid = usid;
			System.out.println(Usid);
			String Location = txtLocation.getText();
			String ServiceType = Service.getValue();
			String Description = txtDescription.getText();
			String Status="Pending";
		    
			NewRequest reqService= new NewRequest(Usid, Location, ServiceType, Description, Status);			
			boolean res = book(reqService); //call method
			if(res==true) {
				showAlert(Alert.AlertType.INFORMATION, "Success", "Request Sent");
			}
			else {
				showAlert(Alert.AlertType.ERROR, "Error", "Failed to request!");
			}
		});
		
		Button btnClose=new Button("Back");
		btnClose.setOnAction((event)->{
			primaryStage.close();
		});
		
		GridPane pane = new GridPane();
		
		pane.setHgap(2);
		pane.setVgap(10);
		
		pane.setConstraints(lblLocation, 0, 0);
		pane.setConstraints(txtLocation, 1, 0);

		
		pane.setConstraints(lblService, 0, 2);
		pane.setConstraints(Service, 1, 2);
		
		pane.setConstraints(lblDescription, 0, 3);
		pane.setConstraints(txtDescription, 1, 3);
		

		
		pane.setConstraints(btnrequest, 1, 10);
		pane.setConstraints(btnClose, 2, 10);
		
		pane.getChildren().add(lblLocation);
		pane.getChildren().add(txtLocation);
		pane.getChildren().add(lblService);
		pane.getChildren().add(Service);
		pane.getChildren().add(lblDescription);
		pane.getChildren().add(txtDescription);
		pane.getChildren().add(btnrequest);
		pane.getChildren().add(btnClose);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);		
		primaryStage.setTitle("Service Request");
		primaryStage.setWidth(650);
		primaryStage.setHeight(450);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public boolean book(NewRequest reqService) {
		
		boolean result = false;
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="ServiceManagement";
		String DBUSER="root";
		String DBPASS="41561011p";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="INSERT INTO servicereq (uid,Service,Description,Location,status) VALUES(?, ?, ?, ?, ?)";
		try {
			Class.forName(DRIVER);//load driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//to establish connection with database
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setInt(1, reqService.getUid());
			pstat.setString(2, reqService.getService());
			pstat.setString(3, reqService.getDescription());
			pstat.setString(4, reqService.getLocation());
			pstat.setString(5, reqService.getStatus());
			
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
