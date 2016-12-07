package api_model;

import api_model.Country;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class Model {
    public static Connection c = null;

    public static Country[] countries = {new Country("USA", "usa"), new Country("UK", "gb"), new Country("France", "fr"), new Country("Germany", "deu"),
            new Country("Italy", "it"), new Country("Spain", "es"), new Country("Australia", "au"), new Country("Argentina", "ar"),
            new Country("Brazil", "br"), new Country("Canada", "ca"), new Country("China", "cn"),
            new Country("India", "in"), new Country("Indonesia", "id"), new Country("Japan", "jp"),
            new Country("Mexico", "mx"), new Country("Russian Federation", "ru"), new Country("Saudi Arabia", "sa"),
            new Country("South Africa", "za"), new Country("Turkey", "tr")};

    public static ArrayList<Indicator> indicators;
    public static String currentIndicator; //default indicator is GDP
    public static Indicator currentObjectIndicator;
    private static int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    public static String currentEndDate = Integer.toString(currentYear); //default ending date
    public static String currentStartDate = Integer.toString(currentYear - 11); //default starting date
    public static ArrayList<Integer> currentCountries = new ArrayList<>();


//    ArrayList<TreeMap<Integer, BigDecimal>> displayedResult = new ArrayList<>(); //FINAL RESULT FOR CHARTS
//    ArrayList<Integer> displayedCountries = new ArrayList<>();
//    TreeMap<String, ArrayList<Integer>> currentQuerry = new TreeMap<String, ArrayList<Integer>>(); //Current indicator code and countries that are querried


    private ArrayList<String> isUpdated = new ArrayList<String>();
    public static Model instance = null;

    private Model() {
        initLabels();
        createDB();
        createInitialTable();
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

    private void initLabels() {
        Indicator gdp = new Indicator("GDP");
        Indicator labour = new Indicator("Labour");
        Indicator prices = new Indicator("Prices");
        Indicator money = new Indicator("Money");
        Indicator trade = new Indicator("Trade");

        gdp.setSubIndicatorsCodes(new String[]{"NY.GDP.MKTP.CD"});
        gdp.setSubIndicatorsLabels(new String[]{"GDP"});

        labour.setSubIndicatorsCodes(new String[]{"SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS"});
        labour.setSubIndicatorsLabels(new String[]{"Employment Rate", "Unemployment Rate"});

        prices.setSubIndicatorsCodes(new String[]{"FP.CPI.TOTL.ZG"});
        prices.setSubIndicatorsLabels(new String[]{"Inflation & Consumer Prices"});

        money.setSubIndicatorsCodes(new String[]{"FR.INR.RINR"});
        money.setSubIndicatorsLabels(new String[]{"Interest Rate"});

        trade.setSubIndicatorsCodes(new String[]{"NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS"});
        trade.setSubIndicatorsLabels(new String[]{"Import", "Export"});

        indicators = new ArrayList<>(Arrays.asList(gdp, labour, prices, money, trade));

    }

    private void createDB() {
        long startTime = System.currentTimeMillis();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Connecion to the databases done in  " + totalTime);
    }

    private void createInitialTable() {
        long startTime = System.currentTimeMillis();
        Statement querry = null;
        try {
            querry = c.createStatement();
            for (int i = 0; i < indicators.size(); i++) {
                for (int j = 0; j < indicators.get(i).getSubIndicatorsCodes().size(); ++j) {
                    String sql = "CREATE TABLE " + eraseDots(indicators.get(i).getSubIndicatorsCodes().get(j)) +
                            " (country        INT(6)    NOT NULL, " +
                            " indicator      TEXT    NOT NULL, " +
                            " year        INT(6), " +
                            " value         DECIMAL," +
                            "PRIMARY KEY (country,indicator,year,value))";
                    querry.executeUpdate(sql);
                }
            }
            querry.close();
            c.close();
        } catch (Exception e) {
            System.out.println("ERROR IN CREATING THE DB");
        }
        System.out.println("SUCCESSFULLY CREATED THE DB");


        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("TABLES CREATED IN " + totalTime);
    }

    private void updateLocal(int countryIndex, String indicator, String startDate, String endDate, TreeMap<Integer, BigDecimal> cachedData) { //First querry will update the local data
        String checkedQuerry = Integer.toString(countryIndex) + "/" + indicator; //+ "/" + startDate + "/" + endDate;

        if (!isUpdated.contains(checkedQuerry)) {
            isUpdated.add(checkedQuerry);
            APIData.getInstance().saveLocally(countryIndex, indicator, cachedData);
        }
    }

    public TreeMap<Integer, BigDecimal> getData(int countryIndex, String indicator, String startDate, String endDate) {
        TreeMap<Integer, BigDecimal> finalHashmap = new TreeMap<>();
        String newQuerriedData = Integer.toString(countryIndex) + "/" + indicator;// + "/" + startDate + "/" + endDate;
        System.out.println(newQuerriedData);
        if (isUpdated.contains(newQuerriedData)) {
            System.out.println("TRYING TO GET DATA FROM CACHE ");
            finalHashmap = CacheData.getInstance().getData(countryIndex, indicator, startDate, endDate);
            if (!finalHashmap.isEmpty()) System.out.println("DATA RETRIEVED FROM CACHE");
            else System.out.println("DATA COULD NOT BE RETRIEVED FROM CACHE");
        } else {
            System.out.println("TRYING TO GET DATA FROM API");
            finalHashmap = APIData.getInstance().getData(countryIndex, indicator, startDate, endDate);
            if (!finalHashmap.isEmpty()) {
                updateLocal(countryIndex, indicator, startDate, endDate, finalHashmap);
                System.out.println("DATA RETRIEVED FROM THE API");
            }
            else {
                System.out.println("COULD NOT CONNECT, TRYING TO GET FROM CACHE");
                finalHashmap = CacheData.getInstance().getData(countryIndex, indicator, startDate, endDate);
                if (!finalHashmap.isEmpty()) System.out.println("DATA RETRIEVED FROM CACHE AFTER RETRY");
                else System.out.println("DATA COULD NOT BE RETRIEVED NEITHER FROM CACHE");
            }
        }
        return finalHashmap;
    }
    public ArrayList<TreeMap<Integer, BigDecimal>> gatherData() {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(10);
      //  displayedResult.clear();
        TreeMap<Integer,BigDecimal>[] res = new TreeMap[currentCountries.size()];
        for (int i = 0; i < currentCountries.size(); ++i) {
            final int finalI = i;
            System.out.println(countries[i].getName());
            executor.execute(new Runnable() {
                @Override
                public void run() {
                   // displayedResult.add(getData(currentCountries.get(finalI), currentIndicator, currentStartDate, currentEndDate));
                    res[finalI] = getData(currentCountries.get(finalI), currentIndicator, currentStartDate, currentEndDate);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("\nFinished all threads,");
        System.out.println("------------------------------------------");
        System.out.println("Operation done successfully");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Gathering all the data for the current request in " + totalTime);
        //return displayedResult;
        ArrayList<TreeMap<Integer,BigDecimal>> result = new ArrayList<>(Arrays.asList(res));
        return result;
    }

    public String eraseDots(String word) {
        return word.replaceAll("\\.", "");
    }


}
