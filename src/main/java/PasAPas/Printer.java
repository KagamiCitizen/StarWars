package PasAPas;

import org.json.JSONArray;
import org.json.JSONObject;

public class Printer {


	// Aller chercher méthode de API.java
	private API api;
	
	public Printer(API api) {
		this.api = api;
	}

	
    public void printDetailsPlanets(JSONArray planetresults) {
        // Parcours des résultats des planètes
        for (int i = 0; i < planetresults.length(); i++) {
        	
            // Extraction des détails d'une planète
            JSONObject planet = planetresults.getJSONObject(i);
            System.out.println("\ninfoPlanet : " + planet);
            
            
            System.out.println("Planet : " + planet.getString("name"));
            System.out.println("Rotation Period : " + planet.getString("rotation_period"));
            System.out.println("Orbital Period : " + planet.getString("orbital_period"));
            System.out.println("Diameter : " + planet.getString("diameter"));
            System.out.println("Gravity : " + planet.getString("gravity"));
            System.out.println("Terrain : " + planet.getString("terrain"));
            System.out.println("Surface water : " + planet.getString("surface_water"));
            System.out.println("Population : " + planet.getString("population"));

                   
            
            JSONArray residents = planet.getJSONArray("residents");
            JSONArray films = planet.getJSONArray("films");

            
            System.out.println("\nResidents :");
            if (!residents.isEmpty()) {
            	for (int h = 0; h < residents.length(); h++) {
	            	String residentUrl = residents.getString(h);
	            	JSONObject resident = api.getPlanetByUrl(residentUrl);
	            	System.out.println(resident.getString("name"));
            	}            
            } else {
            	System.out.println("\nNo data available");
            }
            
            System.out.println("\nFilms :");
            if (!films.isEmpty()) {
            	for (int h = 0; h < films.length(); h++) {
	            	String filmUrl = films.getString(h);
	            	JSONObject film = api.getPlanetByUrl(filmUrl);
	            	System.out.println(film.getString("title"));
            	}            
            } else {
            	System.out.println("\nNo data available");
            }
            
            
            System.out.println("\n");
        }
    }
    
    
    private void printArrayValues(JSONArray jsonArray, String key) {
        if (jsonArray != null) {
            // Parcours des valeurs du tableau
            for (int j = 0; j < jsonArray.length(); j++) {
                String value = jsonArray.getString(j);
                System.out.println(value);
            }
            // Si le tableau est vide, afficher un message
            if (jsonArray.length() == 0) {
                System.out.println("No results");
            }
        }
    }
    
    
    
 }