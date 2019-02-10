package com.hogo.portal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.daro.common.ui.UIError;

public class DB {

	static Connection connection;

	public static void close() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				UIError.showError("DB Fehler", e);
			}
		}
	}

	public static Connection test(String url, String user, String password) throws Exception {
		return DriverManager.getConnection(url, user, password);
	}

	public static void open() throws Exception {
		if (connection == null) {
			String host = "localhost";
//			host = "10.0.0.15";
			connection = test("jdbc:postgresql://"+host+"/portal", "postgres", "Passw0rd");
			AbstractDBModel.connection = connection;
		}
	}

}
