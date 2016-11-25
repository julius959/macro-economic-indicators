package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class Model {
    public static String[] countries = {"USA", "UK", "France", "Germany", "Italy", "Spain"};
    public static String[] countryCodes = {"usa", "gb", "fr", "deu", "it", "es"};
    public static String[] indicatorCodes = {"NY.GDP.MKTP.CD", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "FP.CPI.TOTL.ZG", "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "FR.INR.RINR"};
    //GDP, EMPLYMENT, UNEMPLOYMENT, INFLATION & CONSUMER PRICES, IMPORT, EXPORT,INTEREST RATE
    public static String[] indicators = new String[indicatorCodes.length]; //Getting the names of the indicators from the JSON result

    public static int currentIndicator = 0; //default indicator is GDP
    public static String currentStartDate = "2006"; //default starting date
    public static String currentEndDate = "2016"; //default ending date
    public static ArrayList<Integer> currentCountries = new ArrayList<>();

    ArrayList<HashMap<String, Double>> displayedResult = new ArrayList<>(); //FINAL RESULT FOR CHARTS

    private ArrayList<String> isUpdated = new ArrayList<String>();
    public static Model instance = null;

    private Model() {
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    private void updateLocal(String fileName, StringBuilder data) { //First querry will update the local data
        if (!isUpdated.contains(fileName)) {
            isUpdated.add(fileName);
            APIData.getInstance().saveLocally(data, fileName);
        }
    }

    public HashMap<String, Double> getData(int countryIndex, int indicatorIndex, String startDate, String endDate) {
         isUpdated.add("usaNY.GDP.MKTP.CD20062016"); //TEST THE CACHE
        HashMap<String, Double> data = new HashMap<>();
        String fileName = countryCodes[countryIndex] + indicatorCodes[indicatorIndex] + startDate + endDate;
        String generatedLink = "countries/" + countryCodes[countryIndex] + "/indicators/" + indicatorCodes[indicatorIndex] + "?date=" + startDate + ":" + endDate + "&per_page=10000&format=json";
        StringBuilder result;
        if (isUpdated.contains(fileName)) result = CacheData.getInstance().getData(fileName);
        else {
            result = APIData.getInstance().getResponse(generatedLink);
            updateLocal(fileName, result);
        }
        try {
            JSONArray array = new JSONArray(result.toString());
            JSONArray array1 = array.getJSONArray(1);
            String in = array1.getJSONObject(0).getJSONObject("indicator").getString("value");
            indicators[indicatorIndex] = in;
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

    public void setCurrentCountries(int[] countries) {
        currentCountries.clear();
        for (int i = 0; i < countries.length ; ++i) {
            currentCountries.add(countries[i]);
        }
    }

    public ArrayList<HashMap<String, Double>> gatherData() {
        displayedResult.clear();
       ExecutorService executor = Executors.newFixedThreadPool(currentCountries.size());
        for (int i = 0; i < currentCountries.size(); ++i) {
            final int finalI = i;
            System.out.println(countries[i]);

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    displayedResult.add(getData(currentCountries.get(finalI), currentIndicator, currentStartDate, currentEndDate));
                }
            });


        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("\nFinished all threads");
        return displayedResult;
    }


}
