package api_model;

import api_model.Country;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;
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
    public static String currentStartDate = "2006"; //default starting date
    public static String currentEndDate = "2016"; //default ending date
    public static ArrayList<Integer> currentCountries = new ArrayList<>();


    ArrayList<HashMap<String, Double>> displayedResult = new ArrayList<>(); //FINAL RESULT FOR CHARTS
    HashMap<String, ArrayList<Integer>> currentQuerry = new HashMap<String, ArrayList<Integer>>(); //Current indicator code and countries that are querried


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
    private void createDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }
    private void createInitialTable(){
        Statement querry =  null;
        try{
            querry = c.createStatement();
            String sql = "CREATE TABLE data " +
                    "(country        INT(6)    NOT NULL, " +
                    " indicator      TEXT    NOT NULL, " +
                    " year        INT(6), " +
                    " value         TEXT," +
                    "PRIMARY KEY (country,indicator,year,value))";
            querry.executeUpdate(sql);
            querry.close();
            c.close();
        }catch (Exception e){
            System.out.println("ERROR IN CREATING THE DB ");
        }
        System.out.println("SUCCESSFULLY CREATED THE DB");
    }

    private void updateLocal(int countryIndex, String indicator, String startDate, String endDate,HashMap<String,Double> cachedData){ //First querry will update the local data
        String checkedQuerry = Integer.toString(countryIndex)+"/"+indicator+"/"+startDate+"/"+endDate;

        if (!isUpdated.contains(checkedQuerry)) {
            isUpdated.add(checkedQuerry);
            APIData.getInstance().saveLocally(countryIndex,indicator,cachedData);
        }
    }

    public HashMap<String, Double> getData(int countryIndex, String indicator, String startDate, String endDate) {
        HashMap<String, Double> finalHashmap = new HashMap<>();
        String newQuerriedData = Integer.toString(countryIndex)+"/"+indicator+"/"+startDate+"/"+endDate;
        System.out.println(newQuerriedData);
        if (isUpdated.contains(newQuerriedData)) {
            System.out.println("TRYING TO GET DATA FROM CACHE ");
            try{
                finalHashmap = CacheData.getInstance().getData(countryIndex,indicator,startDate,endDate);
                System.out.println("DATA RETRIEVED FROM CACHE");
            }catch (Exception e){
                System.out.println("DATA COULD NOT BE RETRIEVED FROM CACHE");
            }

        }
        else {

                System.out.println("TRYING TO GET DATA FROM API");
                finalHashmap = APIData.getInstance().getData(countryIndex,indicator,startDate,endDate);
                updateLocal(countryIndex,indicator,startDate,endDate,finalHashmap);
                if(!finalHashmap.isEmpty())System.out.println("DATA RETRIEVED FROM THE API");
                else{
                    System.out.println("COULD NOT CONNECT, TRYING TO GET FROM CACHE");

                        finalHashmap = CacheData.getInstance().getData(countryIndex,indicator,startDate,endDate);
                        if(!finalHashmap.isEmpty())System.out.println("DATA RETRIEVED FROM CACHE AFTER RETRY");
                        else System.out.println("DATA COULD NOT BE RETRIEVED NEITHER FROM CACHE");

                }



            }


    return finalHashmap;
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
