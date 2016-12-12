package api_model;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vlad Niculescu
 * Created by Vlad Niculescu on 24/11/2016.
 */
public class Model {
    /**
     * Variable that represents the connection to the SQL database
     */
    private  Connection c = null;
    /**
     * Static array of Country objects.
     */
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
    /**
     * Static ArrayList of Indicator objects
     */
    public static ArrayList<Indicator> indicators;
    /**
     * Static String that represents the current indicator code that is queried.
     */
    public static String currentIndicator;
    /**
     * Static Indicator object that represents the current indicator that is queried.
     */
    public static Indicator currentObjectIndicator;
    /**
     * Static integer that represents the current year that is configured on the machine.
     */
    public static int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    /**
     * Static ArrayList containing all indexes of the countries that are currently queried.
     */
    public static ArrayList<Integer> currentCountries = new ArrayList<>();
    /**
     * Static HashMap that contains each indicator and the corresponding time range that is being queried.
     */
    public static HashMap<String,TimeRange> timeRanges = new HashMap<>();
    /**
     * ArrayList of queries (country + indicator) that helps differentiate the queries that has already been made in the current application instance and the new ones.
     */
    private ArrayList<String> isUpdated = new ArrayList<String>();
    /**
     * HashMap that contains the queries and the corresponding time range that has already been queried for. Helps for deciding whether or not the data should be retrieved from cache or from API.
     */
    private  HashMap<String,TimeRange> checked = new HashMap<>();
    /**
     * Instance of the current Model object.
     */
    public static Model instance = null;
    /**
     * Boolean that checks if the current queried data returns an empty TreeMap or not. Helps decide if the data is missing when there is no internet connection.
     */
    public boolean emptyData = false;

    /**
     * Private Model constructor that is instantiated only once. It also calls several methods that initialize the data.
     */
    private Model() {
        initIndicatorLabels();
        sortCountries();
        createDB();
        createInitialTable();
        initTimeRanges();
    }
    /**
     * Static method used to create an instance of the Model class or retrieve the existing one. Maintains only one existing instance of the current class.
     * @return Returns the current instance of this class or a new one in case none existed already.
     */
    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }
    /**
     * Sorts the countries in an alphabetical order so they can be prepared for the UI.
     */
    private void sortCountries(){
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
    /**
     * Initializes the Indicator objects, sets the principal categories of indicators, the lists of indicators coresponding to the chosen categories, their labels and their units of measurements that have to be displayed in the charts.
     */
    private void initIndicatorLabels() {
        Indicator gdp = new Indicator("GDP");
        Indicator labour = new Indicator("Labour");
        Indicator prices = new Indicator("Prices");
        Indicator money = new Indicator("Money");
        Indicator trade = new Indicator("Trade");
        Indicator gov = new Indicator("Government");
        Indicator poverty = new Indicator("Poverty");
        gdp.setSubIndicatorsCodes(new String[]{"NY.GDP.MKTP.CD","NY.GDP.PCAP.CD","NY.GDP.MKTP.KD.ZG"});
        gdp.setSubIndicatorsLabels(new String[]{"GDP","GDP per capita","GDP growth"});
        gdp.setSubIndicatorUnits(new String[]{"(USD Billion)","(USD)","(annual %)"});
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
    /**
     * Initializes the HashMap containing all the possible indicators and their corresponding queried time range.
     */
    private void initTimeRanges(){
        for(int i=0;i<indicators.size();++i){
            for(int j=0;j<indicators.get(i).getSubIndicatorsCodes().size();++j) {
                timeRanges.put(indicators.get(i).getSubIndicatorsCodes().get(j),new TimeRange());
            }
        }
    }

    /**
     * Creates the SQL database or opens the existing one.
     */
    private void createDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
        } catch (Exception e) {
            System.exit(0);
        }
    }

    /**
     * Creates the initial tables needed for the application. Several tables for each existent indicator
     */
    private void createInitialTable() {
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
        } catch (Exception e) {}
    }
    /**
     * Inserts the data in the local database
     * <p>In case some data is already there it is updated.
     * This operation is made for each unique query after the starting of the application and only if there is internet connection
     * It also checks for the time ranges that were queried</p>
     * @param countryIndex Country index from the array of countries
     * @param indicator Indicator code as a string used for the current update
     * @param startDate Start year as a string
     * @param endDate End year as a string
     * @param cachedData TreeMap the contains the data which needs to be saved
     */
    private void updateLocal(int countryIndex, String indicator, String startDate, String endDate, TreeMap<Integer, BigDecimal> cachedData) {
        String checkedQuerry = Integer.toString(countryIndex) + "/" + indicator;
        if( !cachedData.isEmpty()){
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
                }
            }
        }
    }
    /**
     * Checks if the current query made for a certain country index, start date and endDate has been already cached within the current application instance or if it is the first time when the query is being made
     * @param querry A string containg the current querry ( country + indicator)
     * @param startDate Start year as a string
     * @param endDate End year as a string
     * @return Returens wheter or not the data has already been cached withing this application instance.
     */
    private boolean isCached(String querry,String startDate,String endDate){
        if(isUpdated.contains(querry)){
            if(Integer.parseInt(checked.get(querry).getStartYear())<=Integer.parseInt(startDate) && Integer.parseInt(checked.get(querry).getEndYear())>=Integer.parseInt(endDate) && Integer.parseInt(checked.get(querry).getStartYear())<=Integer.parseInt(endDate) &&
                    Integer.parseInt(checked.get(querry).getEndYear())>=Integer.parseInt(startDate)) return true;
        }
        return false;
    }
    /**
     * Handles the process of retrieving data
     * <p>The data is retrieved either from api or local cache considering that there is an internet connection available, there is data in cache or not or if the current query has
     * already been made within the current instance of the application</p>
     * @param countryIndex Index of the country from the array of countries
     * @param indicator The indicator code that is being used for the query
     * @param startDate The start year used for the query
     * @param endDate The end year used for the query
     * @return Returns a TreeMap containing the final result. An year Integer as the key and a BigDecimal number as the corresponding value.
     */
    public TreeMap<Integer, BigDecimal> getData(int countryIndex, String indicator, String startDate, String endDate) {
        TreeMap<Integer, BigDecimal> finalHashmap = new TreeMap<>();
        String newQuerriedData = Integer.toString(countryIndex) + "/" + indicator;// + "/" + startDate + "/" + endDate;
        if (isCached(newQuerriedData,startDate,endDate)) {
            finalHashmap = CacheData.getInstance().getData(countryIndex, indicator, startDate, endDate);
            if (!finalHashmap.isEmpty()) emptyData = false;
            else System.out.println("DATA COULD NOT BE RETRIEVED FROM CACHE");
        } else {
            finalHashmap = APIData.getInstance().getData(countryIndex, indicator, startDate, endDate);
            if (!finalHashmap.isEmpty()) {
                emptyData = false;
                updateLocal(countryIndex, indicator, startDate, endDate, finalHashmap);
            } else {
                emptyData = true;
                finalHashmap = CacheData.getInstance().getData(countryIndex, indicator, startDate, endDate);
            }
        }
        return finalHashmap;
    }

    /**
     * Methods that gathers the data for all the current countries selected and the indicator.
     * <p>The data is retrieved using multithreading so the data is being retrieved simultaneously either from the api or from cache. The executor service handles the all the tasks being executed in parallel.
     * </p>
     * @return Returns an ArrayList containing the data for each selected country in a TreeMap.
     */
    public ArrayList<TreeMap<Integer, BigDecimal>> gatherData() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        TreeMap<Integer, BigDecimal>[] res = new TreeMap[currentCountries.size()];
        for (int i = 0; i < currentCountries.size(); ++i) {
            final int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    res[finalI] = getData(currentCountries.get(finalI), currentIndicator, timeRanges.get(currentIndicator).getStartYear(), timeRanges.get(currentIndicator).getEndYear());
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
        ArrayList<TreeMap<Integer, BigDecimal>> result = new ArrayList<>(Arrays.asList(res));
        return result;
    }
    /**
     * Short method that erases the dots in the indicator codes so they can be used as the SQL table names when they are created or retrieved from.
     * @param word the word (indicator code) that has to be modified.
     * @return Returns the current indicator code after all dots have been erased.
     */
    public String eraseDots(String word) {
        return word.replaceAll("\\.", "");
    }


}
