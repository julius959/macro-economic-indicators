package sample;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class APIData {
    public static APIData instance = null;
    private String apiBody = "http://api.worldbank.org/";

    private APIData() {}

    public static APIData getInstance() {
        if (instance == null)
            instance = new APIData();
        return instance;
    }

    public StringBuilder getResponse(String apiLink) {
      //  System.out.println("RETRIEVED FROM API");
        StringBuilder sb = new StringBuilder();
        String finalLink = apiBody + apiLink;
      //  System.out.println(finalLink);
        try {
            URL url = new URL(finalLink);
            System.out.println(finalLink);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (Exception e) {
            System.out.println("Could not connect");
        }
        return sb;
    }

    public void saveLocally(StringBuilder sb, String fileName) {
        try {
            FileWriter cache = new FileWriter(fileName + ".json");
            cache.write(sb.toString());
            cache.flush();
            cache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
