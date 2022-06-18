package it.polito.tdp.interrail_java.model;

import java.util.Objects;

public class Country {
	
	private String countryName;
	
	public Country(String country) {
		this.countryName = country;	
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String toString() {
		return this.countryName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(countryName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return Objects.equals(countryName, other.countryName);
	}
}