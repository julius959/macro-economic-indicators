package api_model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class APIData {
    public static APIData instance = null;
    private String apiBody = "http://api.worldbank.org/";

    private APIData() {
    }

    public static APIData getInstance() {
        if (instance == null)
            instance = new APIData();
        return instance;
    }

    public StringBuilder getResponse(String apiLink) {
        long startTime = System.currentTimeMillis();
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
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Getting response from server  " + totalTime);
        return sb;
    }

    public TreeMap<Integer, BigDecimal> getData(int countryIndex, String indicator, String startDate, String endDate) {
        TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
        String generatedLink = "countries/" + Model.getInstance().countries[countryIndex].getCode() + "/indicators/" + indicator + "?date=" + startDate + ":" + endDate + "&per_page=10000&format=json";
        StringBuilder result = APIData.getInstance().getResponse(generatedLink);
        try {
            JSONArray array = new JSONArray(result.toString());
            JSONArray array1 = array.getJSONArray(1);
            for (int i = 0; i < array1.length(); ++i) {
                JSONObject currentObject = array1.getJSONObject(i);
                System.out.println(array1);
                try {
                    BigDecimal value = currentObject.getBigDecimal("value");
                    System.out.println("VALUES ARE " + value);
                    data.put(Integer.parseInt(currentObject.getString("date")), approximateValues(value,indicator)); // CONVERT INTO POUNDS
                } catch (Exception e) {
                    System.out.printf(currentObject.getString("date") + " has no values");
                }

            }
        } catch (Exception e) {
            System.out.println("Can not build the result");
        }
        System.out.println(data);
        return data;


    }
    private BigDecimal approximateValues(BigDecimal value,String indicator){
        if(indicator=="NY.GDP.MKTP.CD")  return  value.divideToIntegralValue(BigDecimal.valueOf(1000000000));
        return value;
    }

    public void saveLocally(int countryIndex, String indicator, TreeMap<Integer, BigDecimal> cachedData) {
        long startTime = System.currentTimeMillis();
        Connection c = null;
        Statement querry = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            querry = c.createStatement();
            for (int i = 0; i < cachedData.size(); ++i) {
             //   System.out.println(cachedData.size() + "  ADDING DATA INTO THE DB for " + Model.getInstance().countries[countryIndex].getName() + " " + Integer.parseInt(cachedData.keySet().toArray()[i].toString()));
                String sql = "INSERT OR REPLACE INTO " + Model.getInstance().eraseDots(indicator) + " (country,indicator,year,value) " + //computeValues(countryIndex,indicator,cachedData);
                        "VALUES (" + countryIndex + ", '" + indicator + "', " + Integer.parseInt(cachedData.keySet().toArray()[i].toString()) + ",'" + cachedData.get(cachedData.keySet().toArray()[i]).toString() + "');";
                querry.executeUpdate(sql);
            }
            querry.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Saving  the current querry in the DB in:   " + totalTime);
    }
}
