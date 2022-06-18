package it.polito.tdp.interrail_java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class City implements Comparable<City>{
	
	private List<Statistic> stats;
	private String slug; // just a case-modified field
	private Integer id;
	private String uic;
	private Double latitude;
	private Double longitude;
	private String parent_station_id;
	private Country country;
	private String time_zone;
	private Boolean is_city;
	private Boolean is_main_station;
	private Boolean is_airport;
	private Boolean is_suggestable;
	private Boolean flixbus_is_enabled;
	private String iata_airport_code;
	private String cityName;
	
	public City(String iNDIC_UR, Integer v2018, Integer v2019, Integer v2020, String slug, Integer id, String uic,
			Double latitude, Double longitude, String parent_station_id, String country, String time_zone,
			Boolean is_city, Boolean is_main_station, Boolean is_airport, Boolean is_suggestable,
			Boolean flixbus_is_enabled, String iata_airport_code, String cityName) {
		super();
		this.stats = new ArrayList<>();
		stats.add( new Statistic(iNDIC_UR, v2018, v2019, v2020) );
		this.slug = slug;
		this.id = id;
		this.uic = uic;
		this.latitude = latitude;
		this.longitude = longitude;
		this.parent_station_id = parent_station_id;
		this.country = new Country(country);
		this.time_zone = time_zone;
		this.is_city = is_city;
		this.is_main_station = is_main_station;
		this.is_airport = is_airport;
		this.is_suggestable = is_suggestable;
		this.flixbus_is_enabled = flixbus_is_enabled;
		this.iata_airport_code = iata_airport_code;
		this.cityName = cityName;
	}

	public List<Statistic> getStats() {
		return stats;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUic() {
		return uic;
	}

	public void setUic(String uic) {
		this.uic = uic;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getParent_station_id() {
		return parent_station_id;
	}

	public void setParent_station_id(String parent_station_id) {
		this.parent_station_id = parent_station_id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public Boolean getIs_city() {
		return is_city;
	}

	public void setIs_city(Boolean is_city) {
		this.is_city = is_city;
	}

	public Boolean getIs_main_station() {
		return is_main_station;
	}

	public void setIs_main_station(Boolean is_main_station) {
		this.is_main_station = is_main_station;
	}

	public Boolean getIs_airport() {
		return is_airport;
	}

	public void setIs_airport(Boolean is_airport) {
		this.is_airport = is_airport;
	}

	public Boolean getIs_suggestable() {
		return is_suggestable;
	}

	public void setIs_suggestable(Boolean is_suggestable) {
		this.is_suggestable = is_suggestable;
	}

	public Boolean getFlixbus_is_enabled() {
		return flixbus_is_enabled;
	}

	public void setFlixbus_is_enabled(Boolean flixbus_is_enabled) {
		this.flixbus_is_enabled = flixbus_is_enabled;
	}

	public String getIata_airport_code() {
		return iata_airport_code;
	}

	public void setIata_airport_code(String iata_airport_code) {
		this.iata_airport_code = iata_airport_code;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return this.cityName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cityName, country);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		return Objects.equals(cityName, other.cityName) && Objects.equals(country, other.country);
	}

	public void addStats(Statistic statistic) {
		if(!this.stats.contains(statistic))
			this.stats.add(statistic);
		Collections.sort(this.stats, new Comparator <Statistic>() {

			@Override
			public int compare(Statistic o1, Statistic o2) {
				return o1.getINDIC_UR().compareTo(o2.getINDIC_UR());
			}
		
		});
	}

	public int getGreaterValueStatByIndic(String indic) {
		int res = -1;
		for(Statistic s : this.stats) {
			if(s.getINDIC_UR().equals(indic))
				res = s.greaterValue();
		}
		return res;
	}

	@Override
	public int compareTo(City other) {
		return this.cityName.compareTo(other.cityName);
	}
}