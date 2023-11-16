package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.mariadb.jdbc.Connection;

public class Database {
	private static Connection conn = null;
	
	public static String typeDB = null;
	
	public Database() {
		try {
			Properties properties = new Properties();
			
			properties.load(new FileReader("database.properties"));
			
			typeDB = properties.getProperty("db");
			String driver = properties.getProperty("driver");
			String dsn = properties.getProperty("dsn");
			String user = properties.getProperty("user");
			String pass = properties.getProperty("pass");
			
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(dsn, user, pass);
			
			createTables();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			Logger.getLogger(Database.class.getName());
		}
		
	}
	
	public static Connection getConnection() {
		
		if(conn == null) new Database();
		
		return conn;
	}
	
	public static void close() {
		if(conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				Logger.getLogger(Database.class.getName());
			}
		}
	}
	
	private void createTables() {
		List<String> sqls = new ArrayList<String>();
		
		if(Database.typeDB.equals("sqlite")) {
			sqls.add("""
					CREATE TABLE IF NOT EXISTS Departamento (
						id_dep INTEGER PRIMARY KEY AUTOINCREMENT, 
						nombre_dep TEXT UNIQUE NOT NULL, 
						jefe INTEGER DEFAULT NULL
					)
					""");
			
			sqls.add(""" 
						CREATE TABLE IF NOT EXISTS Empleado (
							id_emple INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre_emple TEXT UNIQUE NOT NULL,
							salario REAL DEFAULT 0.0,
							departamento INTEGER DEFAULT NULL
						)
					""");
			
			sqls.add(""" 
						CREATE TABLE IF NOT EXISTS Proyecto (
							id_proyecto INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT UNIQUE NOT NULL
						)
					""");
			
			if(Database.typeDB.equals("mariadb")) {
				sqls.add("""
							CREATE TABLES IF NOT EXISTS Departamento (
							id_dep INT PRIMARY KEY AUTO_INCREMENT,
							nombre_dep VARCHAR(255) UNIQUE NOT NULL,
							jefe INT
							
							)
						""");
			}
		}
	}
	
}
