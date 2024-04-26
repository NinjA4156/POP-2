package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ViewService extends Application {
	
	public static void main(String[] args) {
		launch(args);//call start
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TableView<NewService> table1= new TableView<NewService>();
		table1.setPrefWidth(1000);
		table1.setPrefHeight(250);
		
		//Table Columns
		TableColumn<NewService, Integer> colSID = new TableColumn<>("Service ID");
		colSID.setCellValueFactory(new PropertyValueFactory<>("serviceid"));
		colSID.setMinWidth(50);
		
		TableColumn<NewService, String> colSName = new TableColumn<>("Service Name");
		colSName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		colSName.setMinWidth(150);
		
		TableColumn<NewService, String> colDesc = new TableColumn<>("Description");
		colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
		colDesc.setMinWidth(150);
		
		TableColumn<NewService, String> colDuration = new TableColumn<>("Duration");
		colDuration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
		colDuration.setMinWidth(50);
		
		TableColumn<NewService, Integer> colCost = new TableColumn<>("Cost");
		colCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
		colCost.setMinWidth(50);
		
		table1.getColumns().addAll(colSID,colSName, colDesc, colDuration, colCost);
		
		//set data
		List<NewService> services = new ArrayList<NewService>();
		services = allRecords();
		//set persons to tabl1
		for(NewService service: services) {
			table1.getItems().add(service);
		}
		
		Button btnClose=new Button("Close");
		btnClose.setOnAction((event)->{
			primaryStage.close();
		});
		
		VBox pane = new VBox();
		pane.getChildren().add(table1);
		pane.getChildren().add(btnClose);
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Available Service");
		primaryStage.setWidth(1050);
		primaryStage.setHeight(350);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public ArrayList allRecords() {
		
		ArrayList<NewService> services = new ArrayList<NewService>();
		
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="ServiceManagement";
		String DBUSER="root";
		String DBPASS="41561011p";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="SELECT * FROM service";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);
			
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				
				int serviceid = rs.getInt("sid");
				String servicename = rs.getString("srname");
				String Description = rs.getString("sdescription");
				String Duration = rs.getString("sduration");
				int Cost=rs.getInt("scost");
				
				NewService service = new NewService(serviceid, servicename, Description, Duration, Cost);				
				services.add(service);
				
			}
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return services;
	}
}
