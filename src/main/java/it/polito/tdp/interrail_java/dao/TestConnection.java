package it.polito.tdp.interrail_java.dao;

import java.sql.Connection;

public class TestConnection{

	public static void main(String[] args) {
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("DB is connected!");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("DB is NOT connected!");
		}
	}
}
