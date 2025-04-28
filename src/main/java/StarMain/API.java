package StarMain;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.json.JSONObject;

public class API {

    public JSONObject getBuilder(String path, String searchquery) {
        try {
            // URL de l'API SWAPI
            System.out.println("path : " + path);
            System.out.println("searchquery : "+ searchquery);
        	
        	String urlString = "https://swapi.tech/api/" + path + "/";
            if (searchquery != null && !searchquery.isEmpty()) {
                urlString += "?search=" + searchquery;
            }
            
            URI uri = new URI(urlString);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            System.out.println("class API (URL): " + conn);
            System.out.println("URI: " + uri);
            conn.setRequestMethod("GET");
            conn.connect();
            

            // Lire la réponse
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder responseBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                responseBuilder.append(scanner.nextLine());
            }
            scanner.close();
            
            System.out.println("On a récupéré la réponse de l'API : " + responseBuilder);

            // Convertir la réponse en JSONObject

            return new JSONObject(responseBuilder.toString());

   
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public JSONObject innerRequest(String uri) {
        // Implémentez ceci si vous devez envoyer des requêtes internes
    	
    	/*
        // AJOUT HERE
        try {
            HttpURLConnection conn = (HttpURLConnection) new URI(uri).toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder responseBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                responseBuilder.append(scanner.nextLine());
            }
            scanner.close();

            return new JSONObject(responseBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace
        }
        //FIN AJOUT HERE
         */

        return null;
    }
    
    
    // AJOUT METHODE GET NAME
    public String getName(String resourceType, int id) {
    	String url = "https://swapi.tech/api/" + resourceType + "/" + id;
    	try {
            JSONObject response = fetch(url);
            return response.getJSONObject("result")
                           .getJSONObject("properties")
                           .getString("name");
        } catch (Exception e) {
            return "Unknown " + resourceType + " " + id;
        }
    }
    
    // AJOUT METHODE TITLE
    public String getTitle(int filmId) {
        String url = "https://www.swapi.tech/api/films/" + filmId;
        try {
            JSONObject response = fetch(url);
            return response.getJSONObject("result")
                           .getJSONObject("properties")
                           .getString("title");
        } catch (Exception e) {
            return "Unknown film " + filmId;
        }
    }

    // AJOUT METHODE LABEL
    public String getLabelFromUrl(String url) {
        try {
            String[] parts = url.split("/");
            String resourceType = parts[parts.length - 2];
            int id = Integer.parseInt(parts[parts.length - 1]);

            if ("films".equals(resourceType)) {
                return getTitle(id);
            } else {
                return getName(resourceType, id);
            }
        } catch (Exception e) {
            return url;
        }
    }

    // AJOUT METHODE FETCH
    private JSONObject fetch(String urlString) throws IOException, URISyntaxException {
        URI uri = new URI(urlString);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder inline = new StringBuilder();
        while (sc.hasNext()) {
            inline.append(sc.nextLine());
        }
        sc.close();
        return new JSONObject(inline.toString());
    }
}
