package it.polito.tdp.interrail_java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class ConnectDB {

	private static String jdbcURL = "jdbc:mysql://localhost/interrail_travel_db";

	private static HikariDataSource ds = null;

	public static Connection getConnection() {

		if (ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			config.setUsername("root");
			config.setPassword("root");
			
			// mysql configurations
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("preprStmtChacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			ds = new HikariDataSource(config);
		}
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			System.err.println("DB connection not reachable!");
			throw new RuntimeException(e);
		}

	}

}