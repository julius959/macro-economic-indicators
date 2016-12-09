package api_model;

import view.ProgressBr;


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
    public static Country[] countries = {new Country("United States of America", "usa"), new Country("United Kingdom", "gb"), new Country("France", "fr"), new Country("Germany", "deu"),
            new Country("Italy", "it"), new Country("Spain", "es"), new Country("Australia", "au"), new Country("Argentina", "ar"),
            new Country("Brazil", "br"), new Country("Canada", "ca"), new Country("China", "cn"),
            new Country("India", "in"), new Country("Indonesia", "id"), new Country("Japan", "jp"),
            new Country("Mexico", "mx"), new Country("Russian Federation", "ru"), new Country("Saudi Arabia", "sa"),
            new Country("South Africa", "za"), new Country("Turkey", "tr"),new Country("Pakistan","pk"),new Country("Republic of Korea","kr"),new Country("Romania","ro"),new Country("Sweden","se"),
            new Country("Switzerland","ch"),new Country("Austria","at"),new Country("Malaysia","my"),new Country("Ukraine","ua"),
            new Country("Thailand","th"),new Country("Greece","gr"),new Country("Ireland","ir"),new Country("Poland","pl")
            ,new Country("Netherlands","nl"),new Country("Singapore","sg"),new Country("Hungary","hu"),new Country("Morocco","ma")
            ,new Country("Croatia","hr"),new Country("Egypt","eg"),new Country("Portugal","pt"),new Country("Qatar","qa")
            ,new Country("Denmark","dk"),new Country("Finland","fi"),new Country("Cyprus","cy"),new Country("Monaco","mc")};

    public static ArrayList<Indicator> indicators;
    public static String currentIndicator; //default indicator is GDP
    public static Indicator currentObjectIndicator;
    public static int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    public int startYear = currentYear - 11;
    public static ArrayList<Integer> currentCountries = new ArrayList<>();
    public static String currency = "$";
    public static HashMap<String,TimeRange> timeRanges = new HashMap<>();
    private ArrayList<String> isUpdated = new ArrayList<String>();
    private  HashMap<String,TimeRange> checked = new HashMap<>();
    public static Model instance = null;

    private Model() {
        initLabels();
        sortCountries();
        createDB();
        createInitialTable();
        initTimeRanges();
    }

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }
    public void sortCountries(){
        List tempCountries =  Arrays.asList(countries);
        java.util.Collections.sort(tempCountries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                 return o1.getName().compareTo(o2.getName());

            }
        });
        for(int i=0;i<countries.length;i++){
            countries[i] = (Country) tempCountries.get(i);
        }

    }

    private void initLabels() {
        Indicator gdp = new Indicator("GDP");
        Indicator labour = new Indicator("Labour");
        Indicator prices = new Indicator("Prices");
        Indicator money = new Indicator("Money");
        Indicator trade = new Indicator("Trade");
        Indicator gov = new Indicator("Government");
        Indicator poverty = new Indicator("Poverty");

        gdp.setSubIndicatorsCodes(new String[]{"NY.GDP.MKTP.CD","NY.GDP.PCAP.CD","NY.GDP.MKTP.KD.ZG"});
        gdp.setSubIndicatorsLabels(new String[]{"GDP","GDP per capita","GDP growth"});
        gdp.setSubIndicatorUnits(new String[]{"(USD Billion)","(USD Billion)","(annual %)"});

        labour.setSubIndicatorsCodes(new String[]{"SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS","SP.POP.TOTL","SP.URB.TOTL.IN.ZS"});
        labour.setSubIndicatorsLabels(new String[]{"Employment ratio", "Unemployment ratio","Total Population","Urban population "});
        labour.setSubIndicatorUnits(new String[]{"(% of total labor force)","(% of total labor force)","People","(% of total)"});

        prices.setSubIndicatorsCodes(new String[]{"FP.CPI.TOTL.ZG", "FP.CPI.TOTL","IC.EXP.COST.CD","IC.IMP.COST.CD"});
        prices.setSubIndicatorsLabels(new String[]{"Inflation", "Consumer Prices","Cost to export","Cost to import"});
        prices.setSubIndicatorUnits(new String[]{"(annual %)","(annual %)","US$ per container","US$ per container"});

        money.setSubIndicatorsCodes(new String[]{"FR.INR.RINR","NY.GNS.ICTR.ZS","GC.XPN.TOTL.GD.ZS","GC.TAX.TOTL.GD.ZS","GC.BAL.CASH.GD.ZS"});
        money.setSubIndicatorsLabels(new String[]{"Real interest rate","Gross savings","Expense","Tax revenue","Cash surplus/deficit"});
        money.setSubIndicatorUnits(new String[]{"(%)","(% of GDP)","(% of GDP)","(% of GDP)","(% of GDP)"});

        trade.setSubIndicatorsCodes(new String[]{"NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS"});
        trade.setSubIndicatorsLabels(new String[]{"Imports of goods and services", "Exports of goods and services"});
        trade.setSubIndicatorUnits(new String[]{"(%)","(%)"});

        gov.setSubIndicatorsCodes(new String[]{"GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS"});
        gov.setSubIndicatorsLabels(new String[]{"Total Central government debt","Total Government expenditure on education"});
        gov.setSubIndicatorUnits(new String[]{"(% of GDP)","(% of GDP)"});

        poverty.setSubIndicatorsCodes(new String[]{"SI.POV.GINI"});
        poverty.setSubIndicatorsLabels(new String[]{"GINI index"});
        poverty.setSubIndicatorUnits(new String[]{"index"});

        indicators = new ArrayList<>(Arrays.asList(gdp,labour,prices,money,trade,gov,poverty));

    }
    private void initTimeRanges(){
        for(int i=0;i<indicators.size();++i){
            for(int j=0;j<indicators.get(i).getSubIndicatorsCodes().size();++j)
            {
                timeRanges.put(indicators.get(i).getSubIndicatorsCodes().get(j),new TimeRange());
            }
        }

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
            checked.put(checkedQuerry,new TimeRange());
            APIData.getInstance().saveLocally(countryIndex, indicator, cachedData);
        }else if(isUpdated.contains(checkedQuerry)) {
            if( Integer.parseInt(checked.get(checkedQuerry).getStartYear())>Integer.parseInt(startDate)){
                checked.get(checkedQuerry).setStartYear(startDate);
                APIData.getInstance().saveLocally(countryIndex,indicator,cachedData);}
            if(Integer.parseInt(checked.get(checkedQuerry).getEndYear())<Integer.parseInt(endDate)){
                checked.get(checkedQuerry).setEndYear(endDate);
                APIData.getInstance().saveLocally(countryIndex,indicator,cachedData);
                System.out.println("UPDATE LOCAL");
            }
        }
        }


    private boolean isCached(String querry,String startDate,String endDate){
        if(isUpdated.contains(querry)){
            if(Integer.parseInt(checked.get(querry).getStartYear())<=Integer.parseInt(startDate) && Integer.parseInt(checked.get(querry).getEndYear())>=Integer.parseInt(endDate) && Integer.parseInt(checked.get(querry).getStartYear())<=Integer.parseInt(endDate) &&
                    Integer.parseInt(checked.get(querry).getEndYear())>=Integer.parseInt(startDate)) return true;
        }
        return false;
    }

    public TreeMap<Integer, BigDecimal> getData(int countryIndex, String indicator, String startDate, String endDate) {
        TreeMap<Integer, BigDecimal> finalHashmap = new TreeMap<>();
        String newQuerriedData = Integer.toString(countryIndex) + "/" + indicator;// + "/" + startDate + "/" + endDate;
        System.out.println(newQuerriedData);
        if (isCached(newQuerriedData,startDate,endDate)) {
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
            } else {
                System.out.println("COULD NOT CONNECT, TRYING TO GET FROM CACHE");
                finalHashmap = CacheData.getInstance().getData(countryIndex, indicator, startDate, endDate);
                if (!finalHashmap.isEmpty()) System.out.println("DATA RETRIEVED FROM CACHE AFTER RETRY");
                else System.out.println("DATA COULD NOT BE RETRIEVED NEITHER FROM CACHE");
            }
        }
        return finalHashmap;
    }

    public ArrayList<TreeMap<Integer, BigDecimal>> gatherData() {


        //  ProgressBr pb = new ProgressBr();

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //  displayedResult.clear();
        TreeMap<Integer, BigDecimal>[] res = new TreeMap[currentCountries.size()];


        for (int i = 0; i < currentCountries.size(); ++i) {
            final int finalI = i;
            System.out.println(countries[i].getName());
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    // displayedResult.add(getData(currentCountries.get(finalI), currentIndicator, currentStartDate, currentEndDate));
                    res[finalI] = getData(currentCountries.get(finalI), currentIndicator, timeRanges.get(currentIndicator).getStartYear(), timeRanges.get(currentIndicator).getEndYear());

                }

            });
            //   pBar.setProgress(i);

        }
        //pBar.activateProgressBar((Task)executor);

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        //    pBar.getDialogStage().close();
        System.out.println("\nFinished all threads,");
        System.out.println("------------------------------------------");
        System.out.println("Operation done successfully");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Gathering all the data for the current request in " + totalTime);
        //return displayedResult;
        ArrayList<TreeMap<Integer, BigDecimal>> result = new ArrayList<>(Arrays.asList(res));
        return result;
    }

    public String eraseDots(String word) {
        return word.replaceAll("\\.", "");
    }


}
