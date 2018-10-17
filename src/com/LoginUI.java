package com;

/*
 * GUI for a simulated login screen. Returns  "Login successful!" to console if username textfield = username1 and password
 * textfield = password1
 * 
 * TODO:
 * Change GUI scene when login is successful or unsuccessful
 * 
 */




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginUI extends Application implements EventHandler<ActionEvent>
{
	public Statement stmt;
	TextField usernameTF = new TextField();
	TextField passwordTF = new TextField();
	

	public Statement getStmt()
	{
		return stmt;
	}

	public void setStmt(Statement stmt)
	{
		this.stmt = stmt;
	}

	@Override
	public void start(Stage primaryStage) {
		initializeDB();
		GridPane gPane = new GridPane();	
		
		gPane.setAlignment(Pos.CENTER);
		gPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gPane.setHgap(5.5);
		gPane.setVgap(5.5);
		
		gPane.add(new Label("Username:"), 0, 0);
		gPane.add(usernameTF, 1, 0);
		gPane.add(new Label("Password"), 0, 2);
		gPane.add(passwordTF, 1, 2);
		
		Button btLogin = new Button("Login");
		gPane.add(btLogin, 1, 3);
		GridPane.setHalignment(btLogin, HPos.RIGHT);
		
		Scene scene = new Scene(gPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		btLogin.setOnAction(this);
		
	}
	
	private void initializeDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook?autoReconnect=true&useSSL=false", "vince", "1234");
			
			setStmt(connection.createStatement());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void handle(ActionEvent event)
	{
		String getUsernameAndPassword = "select userName, password from users";
		
		try
		{
			ResultSet rSet = stmt.executeQuery(getUsernameAndPassword);
			String username = "";
			String password = "";
			
			if(rSet.next()) {
				username = rSet.getString(1);
				password = rSet.getString(2);
			}
			
			if(usernameTF.getText().equalsIgnoreCase(username) && passwordTF.getText().equalsIgnoreCase(password)) {
				System.out.println("Login successful!");
			}
			else {
				System.out.println("Login unsuccessful!");
			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}		
	

	
	
	
}


