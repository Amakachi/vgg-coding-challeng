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
			statement.execute("DROP TABLE IF EXISTS Project");
			statement.executeUpdate(
					"CREATE TABLE Project(" +
							"id INTEGER  Primary key, " +
							"name varchar(100) not null," +
							"description varchar(500) not null, " +
							"completed bit) "
			);
			statement.execute("DROP TABLE IF EXISTS Actions");
			statement.executeUpdate(
					"CREATE TABLE Actions(" +
							"id INTEGER  Primary key, " +
							"note varchar(100) not null," +
							"description varchar(500) not null, " +
							"project_id INTEGER," +
							"CONSTRAINT fk_project FOREIGN KEY (project_id)\n" +
							"    REFERENCES Project(id) ) "
			);
			statement.executeUpdate(
					"INSERT INTO UsersTable " +
							"(username,password) " +
							"VALUES " + "('toks','toks')"
			);
			statement.close();
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
