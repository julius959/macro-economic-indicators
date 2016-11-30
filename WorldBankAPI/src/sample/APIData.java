package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;

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
    public HashMap<String,Double> getData(int countryIndex, String indicator, String startDate, String endDate){
        HashMap<String,Double> data = new HashMap<>();
        String generatedLink = "countries/" + Model.getInstance().countries[countryIndex].getCode() + "/indicators/" + indicator + "?date=" + startDate + ":" + endDate + "&per_page=10000&format=json";
        StringBuilder result = APIData.getInstance().getResponse(generatedLink);
        try {
            JSONArray array = new JSONArray(result.toString());
            JSONArray array1 = array.getJSONArray(1);
            //   String in = array1.getJSONObject(0).getJSONObject("indicator").getString("value");

            for (int i = 0; i < array1.length(); ++i) {
                JSONObject currentObject = array1.getJSONObject(i);
                if (!currentObject.getString("value").equals("null"))
                    data.put(currentObject.getString("date").toString(), Double.parseDouble(currentObject.getString("value").toString()));
            }
        } catch (Exception e) {
            System.out.println("Can not build the result");
        }

        return data;


    }
    public void saveLocally(int countryIndex, String indicator,HashMap<String,Double> cachedData){
        Connection c = null;
        Statement querry =null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            querry = c.createStatement();
            for(int i=0;i<cachedData.size()-1;++i) {

                System.out.println(cachedData.size() +"  ADDING DATA INTO THE DB for "+Model.getInstance().countries[countryIndex].getName()+" " +Integer.parseInt(cachedData.keySet().toArray()[i].toString()));

              /*  String sql = "IF EXISTS (SELECT * FROM data WHERE year='"+Integer.parseInt(cachedData.keySet().toArray()[i].toString())+"' AND country = "+countryIndex+" AND indicator = '"+indicator+"') " +
                        " BEGIN UPDATE data SET value ="+ cachedData.get(cachedData.keySet().toArray()[i].toString())+"  WHERE year="+Integer.parseInt(cachedData.keySet().toArray()[i].toString())+"; " +
                        " END ELSE "+ */
                 String sql = "INSERT OR REPLACE INTO data (country,indicator,year,value) " +
                        "VALUES (" + countryIndex + ", '" + indicator + "', " + Integer.parseInt(cachedData.keySet().toArray()[i].toString())+",'" + cachedData.get(cachedData.keySet().toArray()[i]).toString() + "');";
            //    System.out.println("OK");
                querry.executeUpdate(sql);
            }

            querry.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           // System.exit(0);
        }
        System.out.println("Records created successfully");



    }
}
