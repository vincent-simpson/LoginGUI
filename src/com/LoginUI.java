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
	Scene sceneMain, sceneSecondary;
	Button btLogin;	
	String loginSuccessfulOrNot = "";
	Stage primaryStage;

	public Statement getStmt()	{return stmt;}

	public void setStmt(Statement stmt)	{this.stmt = stmt;}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initializeDB();

		createMainScene();

		this.primaryStage.setScene(sceneMain);
		this.primaryStage.show();
	}

	private void createSecondaryScene() {
		GridPane gPaneSecondary = new GridPane();
		Button okButton = new Button("OK");

		gPaneSecondary.setAlignment(Pos.CENTER);
		gPaneSecondary.setPadding(new Insets(11, 12, 13, 14));
		gPaneSecondary.setHgap(5);
		gPaneSecondary.setVgap(5);

		gPaneSecondary.add(new Label(loginSuccessfulOrNot), 1, 0);
		gPaneSecondary.add(okButton, 1, 1);
		GridPane.setHalignment(okButton, HPos.CENTER);

		okButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				primaryStage.setScene(sceneMain);
				primaryStage.show();
			}
		});

		sceneSecondary = new Scene(gPaneSecondary);
	}


	private void createMainScene() {
		GridPane gPaneMain = new GridPane();	

		gPaneMain.setAlignment(Pos.CENTER);
		gPaneMain.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gPaneMain.setHgap(5.5);
		gPaneMain.setVgap(5.5);

		gPaneMain.add(new Label("Username:"), 0, 0);
		gPaneMain.add(usernameTF, 1, 0);
		gPaneMain.add(new Label("Password"), 0, 2);
		gPaneMain.add(passwordTF, 1, 2);

		btLogin = new Button("Login");
		btLogin.setOnAction(new EventHandler<ActionEvent>()
		{
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
						loginSuccessfulOrNot = "Login successful!";
						createSecondaryScene();
						primaryStage.setScene(sceneSecondary);;

					}
					else {
						loginSuccessfulOrNot = "Login unsuccessful!";
						createSecondaryScene();
						primaryStage.setScene(sceneSecondary);;

					}
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}});

		gPaneMain.add(btLogin, 1, 3);
		GridPane.setHalignment(btLogin, HPos.RIGHT);

		sceneMain = new Scene(gPaneMain);



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
					loginSuccessfulOrNot = "Login successful!";
					createSecondaryScene();
					this.primaryStage.setScene(sceneSecondary);;

				}
				else {
					System.out.println("Login unsuccessful!");
					loginSuccessfulOrNot = "Login unsuccessful!";
					createSecondaryScene();
					this.primaryStage.setScene(sceneSecondary);;

				}
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}		


		public static void main(String[] args) {
			Application.launch(args);
		}


	}


