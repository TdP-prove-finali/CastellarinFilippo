package it.polito.tdp.interrail_java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.interrail_java.model.City;
import it.polito.tdp.interrail_java.model.Country;
import it.polito.tdp.interrail_java.model.Statistic;

public class InterrailDAO {
	
	public List<City> getCitiesByCountry(Country country, Map<String, City> idMap) {
		String sql = "SELECT * "
				+ "FROM city_stations "
				+ "WHERE country=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, country.getCountryName());
			ResultSet rs = st.executeQuery();
			
			List<City> result = new ArrayList<>();

			while(rs.next()) {
				double lat;
				double lon;
				try {
					lat = Double.parseDouble(rs.getString("latitude"));
					lon = Double.parseDouble(rs.getString("longitude"));
				} catch(NumberFormatException e) {
					continue;
				}
				City cit = new City(rs.getString("INDIC_UR"), rs.getInt("V2018"), rs.getInt("V2019"), rs.getInt("V2020"), rs.getString("slug"), rs.getInt("id"), rs.getString("uic"), lon, lat, 
						rs.getString("parent_station_id"), rs.getString("country"), rs.getString("time_zone"), rs.getBoolean("is_city"), rs.getBoolean("is_main_station"), 
						rs.getBoolean("is_airport"), rs.getBoolean("is_suggestable"), rs.getBoolean("flixbus_is_enabled"), rs.getString("iata_airport_code"), rs.getString("cityName"));
				
				if(idMap.get(rs.getString("cityName"))==null) {
	
					idMap.put(rs.getString("cityName"), cit);

				}
				else {
					City c = idMap.get(rs.getString("cityName"));
					Statistic s = new Statistic(rs.getString("INDIC_UR"), rs.getInt("V2018"), rs.getInt("V2019"), rs.getInt("V2020"));
					if(!c.getStats().contains(s)) {
						c.addStats(s);
					}
				}
				
				if(!result.contains(cit)) {
					result.add(cit);
				}
			}
			rs.close();
			st.close();
			conn.close();
			return result;

		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DB Error!");
		}
	}

	/**
	 * Loads all cities present in the database in the {@code idMap}
	 * @param idMap
	 * @return a list of cities added
	 */
	public List<City> loadAllCities(Map<String, City> idMap) {
		String sql = "SELECT * "
				+ "FROM city_stations";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			List<City> result = new ArrayList<>();

			while(rs.next()) {
				double lat;
				double lon;
				try {
					// values are inverted in db unfortunately
					lat = Double.parseDouble(rs.getString("latitude"));
					lon = Double.parseDouble(rs.getString("longitude"));
				} catch(NumberFormatException e) {
					continue;
				}
				if(idMap.get(rs.getString("cityName"))==null) {
					idMap.put(rs.getString("cityName"), new City(rs.getString("INDIC_UR"), rs.getInt("V2018"), rs.getInt("V2019"), rs.getInt("V2020"), rs.getString("slug"), rs.getInt("id"), rs.getString("uic"), lon, lat, 
							rs.getString("parent_station_id"), rs.getString("country"), rs.getString("time_zone"), rs.getBoolean("is_city"), rs.getBoolean("is_main_station"), 
							rs.getBoolean("is_airport"), rs.getBoolean("is_suggestable"), rs.getBoolean("flixbus_is_enabled"), rs.getString("iata_airport_code"), rs.getString("cityName")));
					
					result.add(new City(rs.getString("INDIC_UR"), rs.getInt("V2018"), rs.getInt("V2019"), rs.getInt("V2020"), rs.getString("slug"), rs.getInt("id"), rs.getString("uic"), lat, lon, 
							rs.getString("parent_station_id"), rs.getString("country"), rs.getString("time_zone"), rs.getBoolean("is_city"), rs.getBoolean("is_main_station"), 
							rs.getBoolean("is_airport"), rs.getBoolean("is_suggestable"), rs.getBoolean("flixbus_is_enabled"), rs.getString("iata_airport_code"), rs.getString("cityName")));
				}
				else {
					City c = idMap.get(rs.getString("cityName"));
					Statistic s = new Statistic(rs.getString("INDIC_UR"), rs.getInt("V2018"), rs.getInt("V2019"), rs.getInt("V2020"));
					if(!c.getStats().contains(s)) {
						c.addStats(s);
					}
				}
			}
			rs.close();
			st.close();
			conn.close();
			return result;

		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DB Error!");
		}
	}

	public List<Country> loadAllCountries() {
		String sql = "SELECT DISTINCT(country) FROM city_stations";
		try {

			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Country> result = new ArrayList<>();

			while(rs.next()) {
				result.add(new Country(rs.getString("country")));				
			}
			rs.close();
			st.close();
			conn.close();
			return result;

		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DB Error!");
		}
	}
}
