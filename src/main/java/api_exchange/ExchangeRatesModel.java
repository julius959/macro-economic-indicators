package api_exchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ExchangeRatesModel{

    private static  StringBuilder getExchangeRates(String date) {

        StringBuilder stringbuilder = new StringBuilder();
        String finalLink = "http://api.fixer.io/" + date + "?base=GBP";
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

    public static HashMap<String, String> getData() {
        HashMap<String, String> exchangeRates = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -2);
        String yesterdaysDate = dateFormat.format(cal.getTime());

        StringBuilder srToday = getExchangeRates(currentDate);
        StringBuilder srYesterday = getExchangeRates(yesterdaysDate);


        try {
            JSONObject jsonObjectToday = new JSONObject(srToday.toString()).getJSONObject("rates");
            JSONObject jsonObjectYesterday = new JSONObject(srYesterday.toString()).getJSONObject("rates");
            Iterator it = jsonObjectToday.keys();
            while (it.hasNext()) {
                Object k = it.next();
                Double rate =  jsonObjectToday.getDouble(k.toString());
                if(jsonObjectYesterday.has(k.toString())){
                    Double yesterdaysRate = jsonObjectYesterday.getDouble(k.toString());
                           if(rate - yesterdaysRate < 0){
                        exchangeRates.put(k.toString(), String.valueOf(rate - (2*rate)));
                    }
                    else{
                        exchangeRates.put(k.toString(), String.valueOf(rate));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return exchangeRates;
    }
}