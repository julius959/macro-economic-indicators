package api_exchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TreeMap;

/**
 * This class works as a model for the display of the exchange rates in the program. The class retrieves data from 
 * the fixer.io API, checks if the rate for a currency has decreased or increased since yesterday before returning the data to caller
 *
 * @author jacobklerfelt
 * Created 2016-12-01
 */
public class ExchangeRatesModel{
    
    /**
     * Method that calls the creates a URL string, calls the api with the URL, retrieves the input and appends it to a StringBuilder which is then returned
     * @param date String date to get exchange rates from
     * @return StringBuilder with the data from the API
     */
    private static  StringBuilder getExchangeRates(String date) {

        StringBuilder stringbuilder = new StringBuilder();
        String finalLink = "http://api.fixer.io/" + date + "?base=GBP"; // String URL that is going to be called
        try {
            URL url = new URL(finalLink);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            stringbuilder.append(bufferedReader.readLine());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return stringbuilder; 
    }
    
    /**
     * Method to retrieve data of the exchange rates
     * @return HashMap exchangeRates with currencies and their corresponding rates. Negative values indicate that the rate has decreased since yesterday
     * have decreased since yesterday
     */
    public static TreeMap<String, String> getData() {
        TreeMap<String, String> exchangeRates = new TreeMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // DateFormat according to API
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime()); // String for current date
        cal.add(Calendar.DATE, -2);
        String yesterdaysDate = dateFormat.format(cal.getTime()); // String for yesterdays date

        StringBuilder srToday = getExchangeRates(currentDate);
        StringBuilder srYesterday = getExchangeRates(yesterdaysDate);


        try {
            JSONObject jsonObjectToday = new JSONObject(srToday.toString()).getJSONObject("rates"); // JSONObject with todays rates
            JSONObject jsonObjectYesterday = new JSONObject(srYesterday.toString()).getJSONObject("rates"); // JSONObject with yesterdays rates
            Iterator iterator = jsonObjectToday.keys();
            while (iterator.hasNext()) { // iterating through all the rates retrieved from API
                Object k = iterator.next();
                Double rate =  jsonObjectToday.getDouble(k.toString());
                if(jsonObjectYesterday.has(k.toString())){ // checking if there is data of the currencies rate from yesterday
                    Double yesterdaysRate = jsonObjectYesterday.getDouble(k.toString());
                           if(rate - yesterdaysRate < 0){ // rate has decreased
                        exchangeRates.put(k.toString(), String.valueOf(rate - (2*rate))); // adding to HashMap the currency and the rate (as a negative value)
                    }
                    else{
                  exchangeRates.put(k.toString(), String.valueOf(rate)); // rate has increased/can not be compared so adding currency and rate to HashMap without changing anything
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return exchangeRates; // returning HashMap containing all the currencies and their exchangerates
    }
}
