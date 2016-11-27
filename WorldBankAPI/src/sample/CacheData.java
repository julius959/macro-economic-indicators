package sample;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

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

    public StringBuilder getData(String fileName) {
        StringBuilder sb = new StringBuilder();
      //  System.out.println("RETRIEVED FROM CACHE");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName + ".json"));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (Exception e) {
            System.out.println("Could not connect");
        }
        return sb;
    }
}
