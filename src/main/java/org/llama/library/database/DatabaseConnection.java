package org.llama.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private String driver;

	private String url;

	private String user;

	private String password;
	
	private Connection connection;


	public DatabaseConnection() {
		
	}

	public DatabaseConnection(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		init();
	}
	
	private void init(){
		try {
			Class.forName(driver).newInstance();
			connection = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getDriver() {
		return driver;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed())
			this.init();
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
