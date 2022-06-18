package it.polito.tdp.interrail_java.dao;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.interrail_java.model.City;
import it.polito.tdp.interrail_java.model.Country;

public class TestDAO {

	public static void main(String[] args) {
		InterrailDAO dao = new InterrailDAO();
		
		Map<String, City> t = new HashMap<String, City>();
		dao.getCitiesByCountry(new Country("IT"), t);
		
		System.out.println(dao.loadAllCities(t));

		System.out.println(t);
		// tests
	}

}
