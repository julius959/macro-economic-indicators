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
import java.util.TreeMap;

/**
 * Created by Vlad-minihp on 24/11/2016.
 */
public class CacheData {
    public static CacheData instance = null;

    private CacheData() {
    }

    public static CacheData getInstance() {
        if (instance == null)
            instance = new CacheData();
        return instance;
    }

    public TreeMap<Integer, Double> getData(int countryIndex, String indicator, String startDate, String endDate) {
        long startTime = System.currentTimeMillis();
        TreeMap<Integer, Double> cachedResult = new TreeMap<>();
        System.out.println("CALLED GET CACHED DATA");
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cachedDB.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            System.out.println("GETTING FROM CACHE FOR " + Model.getInstance().countries[countryIndex].getName());
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT year,value FROM " + Model.getInstance().eraseDots(indicator) + " WHERE year>= " + startDate + " AND year<=" + endDate + " AND country=" + countryIndex + " AND indicator='" + indicator + "';");
            while (rs.next()) {
                System.out.println("GETTING THE DATA FROM THE DB");
                int year = rs.getInt("year");
                String value = rs.getString("value");
                cachedResult.put(year, Double.parseDouble(value));
                System.out.println("Year = " + year);
                System.out.println("Value = " + value);

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Getting data from cache in  " + totalTime);
        return cachedResult;
    }

}
