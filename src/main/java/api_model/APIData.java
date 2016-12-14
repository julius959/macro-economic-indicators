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
import java.util.TreeMap;

/**
 * @author Vlad Niculescu
 * Created by Vlad Niculescu on 24/11/2016.
 */
public class APIData {
    /**
     * The static instance of the current class that has to be created only once.
     */
    public static APIData instance = null;
    /**
     * The string that contains the main body of the World Bank's api
     */
    private String apiBody = "http://api.worldbank.org/";
    /**
     * Private constructor so it can not be instantiated from outside.
     */
    private APIData() {}

    /**
     * Method that creates an instance of the current class if there isn't one existing yet. Otherwise return the current instance of the class.
     * @return Returns the current instance of this class
     */
    public static APIData getInstance() {
        if (instance == null)
            instance = new APIData();
        return instance;
    }
    /**
     * Method that takes as a parameter the entire api link and, makes a call on the api link.
     * @param apiLink The entire api link required to make the call
     * @return Retrieves a StringBuilder containing the response. In our case a string in the JSON format.
     */
    private StringBuilder getResponse(String apiLink) {
        StringBuilder sb = new StringBuilder();
        String finalLink = apiBody + apiLink;
        try {
            URL url = new URL(finalLink);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (Exception e) {
        }
        return sb;
    }
    /**
     * Returns the data given the paramenters
     * <p>Method that takes the countryIndex, indicator, startDate and endDate as arguments, gets the response from the api using getResponse() method and parses the JSON string.</p>
     * @param countryIndex The index of the country according to the array stored into the model
     * @param indicator The string containing the indicator
     * @param startDate A string representing the start year from which you want the data to be displayed
     * @param endDate A string representing the end year until which you want the data to be displayed.
     * @return  As a return for a given country the result the data
     * is stored into a TreeMap containing the year as a key and the coresponding value from that year according to the selected indicator.
     */
    public TreeMap<Integer, BigDecimal> getData(int countryIndex, String indicator, String startDate, String endDate) {
        TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
        String generatedLink = "countries/" + Model.getInstance().countries[countryIndex].getCode() + "/indicators/" + indicator + "?date=" + startDate + ":" + endDate + "&per_page=10000&format=json";
        StringBuilder result = APIData.getInstance().getResponse(generatedLink);
        if(Integer.parseInt(startDate)<=Integer.parseInt(endDate)){
        try {
            JSONArray array = new JSONArray(result.toString());
            JSONArray array1 = array.getJSONArray(1);
            for (int i = 0; i < array1.length(); ++i) {
                JSONObject currentObject = array1.getJSONObject(i);
                try {
                    BigDecimal value = currentObject.getBigDecimal("value");
                    data.put(Integer.parseInt(currentObject.getString("date")), approximateValues(value,indicator)); // CONVERT INTO POUNDS
                } catch (Exception e) {}
            }
        } catch (Exception e) {
        }
        }
        return data;
    }
    /**
     * Short method that modifies the values so they are more easy to be read by the user.
     * @param value Actual value that has to be modified
     * @param indicator Indicator code parameter used to differentiate between the modifications that have to be done.
     * @return Returns the modified value.
     */
    private BigDecimal approximateValues(BigDecimal value,String indicator){
        if(indicator=="NY.GDP.MKTP.CD")  return  value.divideToIntegralValue(BigDecimal.valueOf(1000000000));
        return value;
    }
    /**
     * Saves the downloaded data from the api into the SQL Database. The data is stored in tables according to the indicator that has been queried.
     * @param countryIndex Country index from the Model array that has to be stored into the table
     * @param indicator Indicator code that is used to generate a specified table with it's name.
     * @param cachedData The actual data that has to be stored inside the table after a certain query has been made. The data comes as a TreeMap.
     */
    public void saveLocally(int countryIndex, String indicator, TreeMap<Integer, BigDecimal> cachedData) {
        Connection c = null;
        Statement querry = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
            c.setAutoCommit(false);
            querry = c.createStatement();
            for (int i = 0; i < cachedData.size(); ++i) {
                String sql = "INSERT OR REPLACE INTO " + Model.getInstance().eraseDots(indicator) + " (country,indicator,year,value) " + //computeValues(countryIndex,indicator,cachedData);
                        "VALUES (" + countryIndex + ", '" + indicator + "', " + Integer.parseInt(cachedData.keySet().toArray()[i].toString()) + ",'" + cachedData.get(cachedData.keySet().toArray()[i]).toString() + "');";
                querry.executeUpdate(sql);
            }
            querry.close();
            c.commit();
            c.close();
        } catch (Exception e) {
        }
    }
}
