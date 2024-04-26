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


public class ViewWorker extends Application {
	
	public static void main(String[] args) {
		launch(args);//call start
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TableView<NewWorker> table1= new TableView<NewWorker>();
		table1.setPrefWidth(1000);
		table1.setPrefHeight(250);
		
		//Table Columns
		TableColumn<NewWorker, Integer> colWID = new TableColumn<>("Worker ID");
		colWID.setCellValueFactory(new PropertyValueFactory<>("worker_id"));
		colWID.setMinWidth(50);
		
		TableColumn<NewWorker, String> colWname = new TableColumn<>("Worker Name");
		colWname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
		colWname.setMinWidth(150);
		
		TableColumn<NewWorker, String> colAddress = new TableColumn<>("Address");
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colAddress.setMinWidth(150);
		
		TableColumn<NewWorker, String> colContact = new TableColumn<>("Contact");
		colContact.setCellValueFactory(new PropertyValueFactory<>("contact")); 
		colContact.setMinWidth(150);

		
		TableColumn<NewWorker, String> colJob = new TableColumn<>("Expertise");
		colJob.setCellValueFactory(new PropertyValueFactory<>("expertise"));
		colJob.setMinWidth(50);
		
		
		
		table1.getColumns().addAll(colWID,colWname, colAddress, colContact, colJob);
		
		//set data
		List<NewWorker> works = new ArrayList<NewWorker>();
		works = allRecords();
		//set persons to tabl1
		for(NewWorker work: works) {
			table1.getItems().add(work);
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
		primaryStage.setTitle("View Worker");
		primaryStage.setWidth(1050);
		primaryStage.setHeight(350);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public ArrayList allRecords() {
		
		ArrayList<NewWorker> works = new ArrayList<NewWorker>();
		
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		String HOST ="localhost";
		int PORT=3306;
		String DATABASE ="ServiceManagement";
		String DBUSER="root";
		String DBPASS="41561011p";
		String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
		String sql="SELECT * FROM worker";
		try {
			Class.forName(DRIVER); //loading driver
			Connection conn = DriverManager.getConnection(URL, DBUSER, DBPASS);//connection with database server
			PreparedStatement pstat = conn.prepareStatement(sql);
			
			ResultSet rs = pstat.executeQuery();
			while(rs.next()) {
				int wid = rs.getInt("wid");
				String workername = rs.getString("fullname");
				String address = rs.getString("address");
				String Contact= rs.getString("contact");
				String expertise = rs.getString("expertise");
				System.out.println(Contact);
				
				NewWorker work = new NewWorker(wid, workername, address, Contact, expertise);				
				works.add(work);
				
			}
			pstat.close();
			conn.close();			
		}
		catch(Exception ex) {
			System.out.println("Error : "+ex.getMessage());
		}
		return works;
	}
}
