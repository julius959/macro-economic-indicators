package api_model;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeMap;

/**
 * @author Vlad Niculescu
 * Created by Vlad Niculescu on 24/11/2016.
 */
public class CacheData {
    /**
     * The static instance of the current class that has to be created only once.
     */
    public static CacheData instance = null;
    /**
     * Private constructor so it can not be instantiated from outside.
     */
    private CacheData() {}
    /**
     * Method that creates an instance of the current class if there isn't one existing yet. Otherwise return the current instance of the class.
     * @return Returns the current instance of this class
     */
    public static CacheData getInstance() {
        if (instance == null)
            instance = new CacheData();
        return instance;
    }
    /**
     * Method that retrieves the data from the local stored SQL Database.
     * @param countryIndex Index of the country in the array stored in the Model class
     * @param indicator Indicator string used to select the table from which the data needs to be retrieved
     * @param startDate Start date string used to query the data from the database
     * @param endDate End date string used to query the data from the database
     * @return Returns a TreeMap that has an year Integer as a key and the corresponding value.
     */

    public TreeMap<Integer, BigDecimal> getData(int countryIndex, String indicator, String startDate, String endDate) {
        TreeMap<Integer, BigDecimal> cachedResult = new TreeMap<>();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT year,value FROM " + Model.getInstance().eraseDots(indicator) + " WHERE year>= " + startDate + " AND year<=" + endDate + " AND country=" + countryIndex + " AND indicator='" + indicator + "';");
            while (rs.next()) {
                int year = rs.getInt("year");
                BigDecimal value = rs.getBigDecimal("value");
                cachedResult.put(year, value);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.exit(0);
        }
        return cachedResult;
    }

}
