package it.polito.tdp.interrail_java.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.interrail_java.dao.InterrailDAO;

public class Model {

	private Graph<City, DefaultWeightedEdge> graph;
	private InterrailDAO dao;
	private Map<String, City> idMap;
	private List<List<City>> solution;
	private static final double TOT = 1100; // max km per day
	private static final double distReachableSameDay = 70; // max km for cities to be visited within the same day
	private double distSolution;

	public Model() {
		dao = new InterrailDAO();
		this.idMap = new HashMap<String, City>();
		distSolution = Double.MAX_VALUE;
	}

	public List<City> getCitiesByCountry(Country country) {
		return this.dao.getCitiesByCountry(country, idMap);
	}

	public List<Country> getCountries() {
		return this.dao.loadAllCountries();
	}

	public void loadCities() {
		this.dao.loadAllCities(idMap);
	}

	public List<Statistic> getStats() {
		return this.idMap.get("Torino").getStats(); // one random city just to have all stats loaded
	}

	public void createGraph(int dot, String indic, City origin) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		// add all cities to the graph
		// --> recursively to maximize interests
		Graphs.addAllVertices(this.graph, this.idMap.values());


		// add edges
		// --> calculating distance using LatLngTool.distance()
		for(City c1 : this.graph.vertexSet()) {
			LatLng coord1 = new LatLng(c1.getLatitude(), c1.getLongitude());
			for(City c2 : this.graph.vertexSet()) {
				if(!c1.equals(c2) && !this.graph.containsEdge(c1, c2)) {
					LatLng coord2 = new LatLng(c2.getLatitude(), c2.getLongitude());
					double distance = LatLngTool.distance(coord1, coord2, LengthUnit.KILOMETER);
					if(distance < TOT) {
						Graphs.addEdge(this.graph, c1, c2, distance);
					}
				}
			}
		}
		System.out.println("Graph created:\n\t- no. vertices: " + this.graph.vertexSet().size() +";");
		System.out.println("\t- no. edges: " + this.graph.edgeSet().size());
	}

	public List<List<City>> searchBetterCities(int dot, String indic, City origin) {
		List<List<City>> partial = new ArrayList<>();
		this.solution = new ArrayList<>();

		ConnectivityInspector<City, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.graph);

		// origin in position 0
		List<City> cities = new ArrayList<>();

		List<City> temp = new ArrayList<>();
		temp.add(origin);
		partial.add(0, temp);
		for(City c : ci.connectedSetOf(origin)) { 
			if(c.equals(origin)) {
				continue;
			}
			for(Statistic s : c.getStats()) {
				if(s.getINDIC_UR().equals(indic) && s.greaterValue()>this.calculateAVGparam(indic) && !cities.contains(c)) { 
					cities.add(c);
				}
			}
		}

		System.out.println("CC: " + cities.size());

		this.searchCities_recursively(dot+1, partial, cities, indic); 

		return this.solution;
	}

	private void searchCities_recursively(int dot, List<List<City>> partial, List<City> cities, String indic) {

		// exit conditions		
		if(partial.size()==dot) {
			if(this.calculateAffinity(partial, indic) > this.calculateAffinity(this.solution, indic) && this.calculateKm(partial) < this.distSolution) {
				this.solution = new ArrayList<>(partial);
				this.distSolution = this.calculateKm(partial);
				System.out.println("Best solution with distance: " + distSolution +"  and weight: " + this.calculateAffinity(this.solution, indic));
//				for(List<City> lc : solution) {
//					for(City c : lc) {
//						System.out.println("\t- " + c);
//					}
//				}
			}
			return;
		}

		if(0 == cities.size()) {
			if(this.calculateAffinity(partial, indic) > this.calculateAffinity(this.solution, indic) && this.calculateKm(partial) < this.distSolution) {
				this.solution = new ArrayList<>(partial);
				this.distSolution = this.calculateKm(partial);
			}
			return;
		}
		
//		-----------------------------------------------------------------------------------
		
		for(int pos=0; pos<cities.size(); pos++) {
			City c = cities.get(pos);
			if(this.isReachable(c, partial, dot) == 1) {
				List<City> temp = new ArrayList<>();

				temp.add(c);
				partial.add(temp);
				cities.remove(c);
				this.searchCities_recursively(dot, partial, cities, indic);

				// back-track
				for(City cTemp : temp)
					cities.add(cTemp);

				partial.remove(temp);

			} else if(this.isReachable(c, partial, dot) == 2) {
				partial.get(partial.size()-1).add(c);
				cities.remove(c);
			}
		}
		return; 
	}

	private double calculateKm(List<List<City>> partial) {
		double res = 0.0;
		for(int i=1; i<partial.size(); i++) {
			List<City> l0 = partial.get(i-1);
			List<City> l1 = partial.get(i);
			res += this.graph.getEdgeWeight(this.graph.getEdge(l0.get(l0.size()-1), l1.get(l1.size()-1)));
		}
		return res;
	}

	private int calculateAffinity(List<List<City>> toCalculate, String indic) {
		int affinity = 0;
		for(int i=0; i<toCalculate.size(); i++) {
			for(City c : toCalculate.get(i)) {
				affinity += c.getGreaterValueStatByIndic(indic);
			}
		}
		return affinity;
	}

	private int isReachable(City c, List<List<City>> partial, int dot) {
		List<City> temp = partial.get(partial.size()-1);
		if(this.graph.containsEdge(c, temp.get(temp.size()-1)) && this.graph.getEdgeWeight(this.graph.getEdge(c, temp.get(temp.size()-1)))<distReachableSameDay)
			return 2;

		if(this.graph.containsEdge(c, temp.get(temp.size()-1)))
			return 1;


		return 0;
	}

	private double calculateAVGparam(String indic) {
		double sum = 0;
		double count = 0;
		for(City c : this.graph.vertexSet()) {
			for(Statistic s : c.getStats()) {
				if(s.getINDIC_UR().equals(indic)) {
					count++;
					sum += s.greaterValue();
				}
			}
		}

		return sum/count;
	}
}