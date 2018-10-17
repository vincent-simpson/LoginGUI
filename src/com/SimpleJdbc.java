package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SimpleJdbc
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "vince", "1234");
		
		Statement statement = connection.createStatement();
		
		ResultSet resultSet = statement.executeQuery
				("select * from users");
		
		while(resultSet.next())
		{
			System.out.println(resultSet.getString(2) + "\t" +
					resultSet.getString(3));
		}
	
		
	}

}
