package com.vgg.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInitializeConfig {

	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize(){
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE IF EXISTS UsersTable");
			statement.executeUpdate(
					"CREATE TABLE UsersTable(" +
					"id INTEGER  Primary key, " +
					"username varchar(30) not null," +
					"password varchar(30) not null)"
					);
			statement.executeUpdate(
					"INSERT INTO UsersTable " +
							"(username,password) " +
							"VALUES " + "('toks','toks123')"
			);
			statement.close();
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
