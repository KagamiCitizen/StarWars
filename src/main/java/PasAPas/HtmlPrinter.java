package PasAPas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class HtmlPrinter {
	
	
	private API api;
	
	public HtmlPrinter(API api) {
		this.api = api;
	}
	
	private String codeHtml;
    
	public String printHtmlDetailsPlanets(JSONObject planetData) {
        if (planetData != null) {
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html><head><title>Planets Data</title></head><body>");
            htmlBuilder.append("<h1>Planets Data</h1>");

            // Vérifie si la clé "results" est présente dans le JSON
            if (planetData.has("results")) {
                JSONArray results = planetData.getJSONArray("results");
                // System.out.println("results : " + results);

                
                // Parcourt chaque objet planète
                for (int i = 0; i < results.length(); i++) {
                    JSONObject planet = results.getJSONObject(i);
                    
                    // DEBUT MODIFICATION
                    
                    htmlBuilder.append("<h2>Name: " + planet.getString("name") + "</h2>");
                    htmlBuilder.append("<p>Rotation Period : " + planet.getString("rotation_period") + "</p>");
                	htmlBuilder.append("<p>Orbital Period : " + planet.getString("orbital_period") + "</p>");
                	htmlBuilder.append("<p>Diameter : " + planet.getString("diameter") + "</p>");
                	htmlBuilder.append("<p>Gravity : " + planet.getString("gravity") + "</p>");
                	htmlBuilder.append("<p>Terrain : " + planet.getString("terrain") + "</p>");
                	htmlBuilder.append("<p>Surface Water : " + planet.getString("surface_water") + "</p>");
                	htmlBuilder.append("<p>Population : " + planet.getString("population") + "</p>");
                    
                    
                    // FIN MODIFICATION
                    
                    // DEBUT EN COMMENTAIRE
                    
                    // Ajoute les résidents                    
                  JSONArray residents = planet.getJSONArray("residents");
                  if (residents.length() > 0) {
                      htmlBuilder.append("<p>").append("Residents: ").append("</p><ul>");
                      for (int j = 0; j < residents.length(); j++) {
                    	  String residentUrl = residents.getString(j);
                    	  JSONObject resident = api.getPlanetByUrl(residentUrl);
                          htmlBuilder.append("<li>").append(resident.getString("name")).append("</li>");
                      }
                      htmlBuilder.append("</ul>");
                  }

                  // Ajoute les films
                  JSONArray films = planet.getJSONArray("films");
                  if (films.length() > 0) {
                      htmlBuilder.append("<p>").append("Films: ").append("</p><ul>");
                      for (int j = 0; j < films.length(); j++) {
                    	  String filmUrl = films.getString(j);
      	            	JSONObject film = api.getPlanetByUrl(filmUrl);
                          htmlBuilder.append("<li>").append(film.getString("title")).append("</li>");
                      }
                      htmlBuilder.append("</ul>");
                  }
                    
                            
                    // FIN EN COMMENTAIRE
                  // System.out.println("codeHtml" + codeHtml);
                  // System.out.println("htmlBuilder" + htmlBuilder);
               }
            }
            htmlBuilder.append("</body></html>");

            // Stocke le contenu HTML dans une variable
            codeHtml = htmlBuilder.toString();
            // System.out.println("HELLO" + codeHtml);
        }
        
        // Renvoie le contenu HTML généré
        return codeHtml;
    }
    
    public void saveHtmlToFile(String htmlContent, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
            System.out.println("HTML content has been saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

}
