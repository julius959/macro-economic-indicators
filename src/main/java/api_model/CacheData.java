package api_model;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class CacheData {
    public static CacheData instance = null;

    private CacheData() {}

    public static CacheData getInstance() {
        if (instance == null)
            instance = new CacheData();
        return instance;
    }

    public HashMap<String,Double> getData(int countryIndex, String indicator, String startDate, String endDate){
        HashMap<String,Double> cachedResult = new HashMap<>();
        System.out.println("CALLED GET CACHED DATA");
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            System.out.println("GETTING FROM CACHE FOR "+Model.getInstance().countries[countryIndex].getName());
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT year,value FROM data WHERE year>= "+startDate+" AND year<="+endDate+" AND country="+countryIndex+" AND indicator='"+indicator+"';" );
            while ( rs.next() ) {
                System.out.println("GETTING THE DATA FROM THE DB");
                int year = rs.getInt("year");
                String  value = rs.getString("value");
                cachedResult.put(Integer.toString(year),Double.parseDouble(value));
                System.out.println( "Year = " + year );
                System.out.println( "Value = " + value );

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return cachedResult;
    }

}
