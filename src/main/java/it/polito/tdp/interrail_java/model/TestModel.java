package it.polito.tdp.interrail_java.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		List<City> cities = model.getCitiesByCountry(new Country("IT"));
		
		for(City c : cities) {
			System.out.println("\t- " + c.toString());
		}
	}
}