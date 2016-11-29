package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class Model {


    public static Country[] countries = {new Country("USA", "usa"), new Country("UK", "gb"), new Country("France", "fr"), new Country("Germany", "deu"),
            new Country("Italy", "it"), new Country("Spain", "es"), new Country("Australia", "au"), new Country("Argentina", "ar"),
            new Country("Brazil", "br"), new Country("Canada", "ca"), new Country("China", "cn"),
            new Country("India", "in"), new Country("Indonesia", "id"), new Country("Japan", "jp"),
            new Country("Mexico", "mx"), new Country("Russian Federation", "ru"), new Country("Saudi Arabia", "sa"),
            new Country("South Africa", "za"), new Country("Turkey", "tr")};

    public static ArrayList<Indicator> indicators;
    public static String currentIndicator; //default indicator is GDP
    public static String currentStartDate = "2006"; //default starting date
    public static String currentEndDate = "2016"; //default ending date
    public static ArrayList<Integer> currentCountries = new ArrayList<>();


    ArrayList<HashMap<String, Double>> displayedResult = new ArrayList<>(); //FINAL RESULT FOR CHARTS
    HashMap<String, ArrayList<Integer>> currentQuerry = new HashMap<String, ArrayList<Integer>>(); //Current indicator code and countries that are querried


    private ArrayList<String> isUpdated = new ArrayList<String>();
    public static Model instance = null;

    private Model() {
        initLabels();
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    private void initLabels() {
        Indicator gdb = new Indicator("GDB");
        Indicator labour = new Indicator("Labour");
        Indicator prices = new Indicator("Prices");
        Indicator money = new Indicator("Money");
        Indicator trade = new Indicator("Trade");

        gdb.setSubIndicatorsCodes(new String[]{"NY.GDP.MKTP.CD"});
        gdb.setSubIndicatorsLabels(new String[]{"GDB"});

        labour.setSubIndicatorsCodes(new String[]{"SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS"});
        labour.setSubIndicatorsLabels(new String[]{"Employment Rate", "Unemployment Rate"});

        prices.setSubIndicatorsCodes(new String[]{"FP.CPI.TOTL.ZG"});
        prices.setSubIndicatorsLabels(new String[]{"Inflation & Consumer Prices"});

        money.setSubIndicatorsCodes(new String[]{"FR.INR.RINR"});
        money.setSubIndicatorsLabels(new String[]{"Interest Rate"});

        trade.setSubIndicatorsCodes(new String[]{"NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS"});
        trade.setSubIndicatorsLabels(new String[]{"Import", "Export"});

        indicators = new ArrayList<>(Arrays.asList(gdb, labour, prices, money, trade));

    }

    private void updateLocal(String fileName, StringBuilder data) { //First querry will update the local data
        if (!isUpdated.contains(fileName)) {
            isUpdated.add(fileName);
            APIData.getInstance().saveLocally(data, fileName);
        }
    }

    public HashMap<String, Double> getData(int countryIndex, String indicator, String startDate, String endDate) {
        HashMap<String, Double> data = new HashMap<>();
        String fileName = countries[countryIndex].getCode() + indicator + startDate + endDate;
        String generatedLink = "countries/" + countries[countryIndex].getCode() + "/indicators/" + indicator + "?date=" + startDate + ":" + endDate + "&per_page=10000&format=json";
        StringBuilder result;
        if (isUpdated.contains(fileName)) result = CacheData.getInstance().getData(fileName);
        else {
            result = APIData.getInstance().getResponse(generatedLink);
            updateLocal(fileName, result);
        }
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

    public void setCurrentCountries(int[] c) {
        currentCountries.clear();
        for (int i = 0; i < c.length; ++i) {
            currentCountries.add(c[i]);
        }

    }

    public ArrayList<HashMap<String, Double>> gatherData() {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        if (!currentQuerry.containsKey(currentIndicator)) {
            displayedResult.clear();
            ArrayList<Integer> currentC = new ArrayList<>();
            currentQuerry.put(currentIndicator, currentC);

        }

        for (int i = 0; i < currentCountries.size(); ++i) {
            final int finalI = i;
            System.out.println(countries[i].getName());
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    if (!currentQuerry.get(currentIndicator).contains(finalI)) {
                        displayedResult.add(getData(currentCountries.get(finalI), currentIndicator, currentStartDate, currentEndDate));
                        currentQuerry.get(currentIndicator).add(currentCountries.get(finalI));

                        System.out.println(currentIndicator + " NEW QUERRY FOR  " + countries[finalI].getName());
                    } else
                        System.out.println(currentIndicator + "HAS ALREADY BEEN QUERRIED FOR " + countries[currentCountries.get(finalI)].getName());


                }
            });


        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("\nFinished all threads,");
        System.out.println("------------------------------------------");
        return displayedResult;
    }


}
