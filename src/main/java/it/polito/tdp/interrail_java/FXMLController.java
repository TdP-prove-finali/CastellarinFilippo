/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.interrail_java;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.interrail_java.model.City;
import it.polito.tdp.interrail_java.model.Country;
import it.polito.tdp.interrail_java.model.Model;
import it.polito.tdp.interrail_java.model.Statistic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="choiceBoxPreferences"
    private ChoiceBox<String> choiceBoxPreferences; // Value injected by FXMLLoader

    @FXML // fx:id="cmbOriginCity"
    private ComboBox<City> cmbOriginCity; // Value injected by FXMLLoader

    @FXML // fx:id="cmbOriginCountry"
    private ComboBox<Country> cmbOriginCountry; // Value injected by FXMLLoader

    @FXML // fx:id="cmbTravelDays"
    private ComboBox<Integer> cmbTravelDays; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    /**
     * Once the country has been selected, this method allow user to select a city
     * @param event
     */
    @FXML
    void handleCountrySelection(ActionEvent event) {
    	this.cmbOriginCity.setDisable(false);
    	Country country = this.cmbOriginCountry.getValue();
    	if(country!=null) {
    		List<City> temp = this.model.getCitiesByCountry(country);
    		Collections.sort(temp);
    		this.cmbOriginCity.getItems().clear();
    		this.cmbOriginCity.getItems().addAll(temp);
    		
    		this.model.loadCities();
    		
    		// load stats
    		for(Statistic s : this.model.getStats()) {
    			if(!this.choiceBoxPreferences.getItems().contains(s.getINDIC_UR()))
    				this.choiceBoxPreferences.getItems().add( s.getINDIC_UR() );
    		}
    	}
    }
    
    @FXML
    void handleCalculate(ActionEvent event) {
    	this.txtResult.clear();
    	
    	City originCity = this.cmbOriginCity.getValue();
    	Country originCountry = this.cmbOriginCountry.getValue();
    	String preferences = this.choiceBoxPreferences.getValue();
    	Integer dot = this.cmbTravelDays.getValue();
    	
    	if(preferences==null) {
    		this.txtResult.setText("Select preferences to continue");
    		return;
    	}
    	if(originCity==null) {
    		this.txtResult.setText("Select origin city to continue.");
    		return;
    	}
    	if(originCountry==null) {
    		this.txtResult.setText("Insert origin country to continue.");
    		return;
    	}
    	if(dot==null) {
    		this.txtResult.setText("Select number of cities you want to visit to continue.");
    		return;
    	}
    	
    	this.model.createGraph(dot, this.choiceBoxPreferences.getValue(), originCity);
    	    	
    	long in = System.currentTimeMillis();
    	List<List<City>> cities = this.model.searchBetterCities(dot, preferences, originCity);
    
    	if(cities.size()!=0) {
    		this.txtResult.appendText("The best cities to visit for you are: ");
    		for(int i=1; i<cities.size(); i++) { // first position is for origin City
    			if(cities.get(i).size() > 1) {
    				this.txtResult.appendText("\n\tThose following cities can be visited on the same day: ");
    				for(City c : cities.get(i)) {
    					this.txtResult.appendText("\n\t\t- " + c.toString());
    				}
    			} else {
    				this.txtResult.appendText("\n\t- " + cities.get(i).get(0).toString());
    			}
    		}
    		System.out.println("Tempo di esecuzione: " + (System.currentTimeMillis() - in) + " ms");
    	} else {
    		this.txtResult.appendText("No results available!");
    	}
    }

    @FXML
    void handleReset(ActionEvent event) {
    	
    	this.choiceBoxPreferences.getItems().clear();
    	this.cmbOriginCity.getItems().clear();
    	this.txtResult.clear();
    	this.cmbTravelDays.getItems().clear();
    	this.cmbOriginCountry.getItems().clear();
    	
    	this.cmbOriginCountry.getItems().addAll(model.getCountries());
    	this.initialize();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert choiceBoxPreferences != null : "fx:id=\"choiceBoxPreferences\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbOriginCity != null : "fx:id=\"cmbOriginCity\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbOriginCountry != null : "fx:id=\"cmbOriginCountry\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbTravelDays != null : "fx:id=\"cmbTravelDays\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        this.loadTravelDays();
        this.cmbOriginCity.setDisable(true);
    }

    public void setModel(Model m) {
    	this.model = m;
    	
    	this.cmbOriginCountry.getItems().addAll(model.getCountries());
    }
    
    private void loadTravelDays() {
    	List<Integer> travelDays = new ArrayList<Integer>();
        Integer[] dot = {4, 5, 7, 10, 14};
        for(int i=0; i<dot.length; i++) {
        	travelDays.add(dot[i]);
        }
        this.cmbTravelDays.getItems().addAll(travelDays);
    }
}
