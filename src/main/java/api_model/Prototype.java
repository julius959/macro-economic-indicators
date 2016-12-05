package api_model;

import api_model.Model;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class Prototype {
    private static String apiLink = "http://api.worldbank.org/countries/all/indicators/SP.POP.TOTL?format=json";


    public static void main(String[] args) {
        //    System.out.println(getResponse(apiLink));
        // System.out.println(Model.getInstance().getData(1,6,"2006","2016"));
        long startTime = System.currentTimeMillis();


        System.out.println(Model.getInstance().getData(0,"NY.GDP.MKTP.CD","2006","2015"));

//        int[] c = {0, 1, 2};
//        Model.getInstance().setCurrentCountries(c);
//        Model.getInstance().currentIndicator = "NY.GDP.MKTP.CD";
//        Model.getInstance().currentStartDate = "2006";
//        Model.getInstance().currentEndDate = "2016";
//        ArrayList res = Model.getInstance().gatherData();
//
//       /* for(int i = 0; i<res.size();++i){
//            System.out.println(res.get(i));
//        }*/
//        System.out.println("NUMBER OF COUNTRIES DISPLAYED : " + res.size());
//
//        int[] c2 = {0, 1, 2, 3};
//        Model.getInstance().setCurrentCountries(c2);
//        Model.getInstance().currentIndicator = "NY.GDP.MKTP.CD";
//        Model.getInstance().currentStartDate = "2006";
//        Model.getInstance().currentEndDate = "2016";
//        ArrayList res2 = Model.getInstance().gatherData();
//
//
//
//      /*  for(int i = 0; i<res2.size();++i){
//            System.out.println(res2.get(i));
//        }*/
//        System.out.println("NUMBER OF COUNTRIES DISPLAYED : " + res2.size());
//
//        int[] c3 = {4, 5};
//        Model.getInstance().setCurrentCountries(c3);
//        Model.getInstance().currentIndicator = "NY.GDP.MKTP.CD";
//        Model.getInstance().currentStartDate = "2006";
//        Model.getInstance().currentEndDate = "2016";
//        ArrayList res3 = Model.getInstance().gatherData();
//
//
///*
//        for(int i = 0; i<res3.size();++i){
//            System.out.println(res3.get(i));
//        }*/
//        System.out.println("NUMBER OF COUNTRIES DISPLAYED : " + res3.size());
//
//
//        int[] c4 = {0, 1, 2, 3, 4, 5};
//        Model.getInstance().setCurrentCountries(c4);
//        Model.getInstance().currentIndicator = "NY.GDP.MKTP.CD";
//        Model.getInstance().currentStartDate = "2006";
//        Model.getInstance().currentEndDate = "2016";
//        ArrayList res4 = Model.getInstance().gatherData();
//
//
//        int[] c5 = {0, 1, 2, 3, 4, 5};
//        Model.getInstance().setCurrentCountries(c4);
//        Model.getInstance().currentIndicator = "NY.GDP.MKTP.CD";
//        Model.getInstance().currentStartDate = "2006";
//        Model.getInstance().currentEndDate = "2016";
//        ArrayList res5 = Model.getInstance().gatherData();
//
//
//
//       /* for(int i = 0; i<res3.size();++i){
//            System.out.println(res4.get(i));
//
//        }*/
//        System.out.println("NUMBER OF COUNTRIES DISPLAYED : " + res5.size());
//        System.out.println(res5);
//


        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);


    }
  /*  public static ArrayList<String> getResponse(String apiLink) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        //DOWNLOAD FROM URL
        try {
            URL url = new URL(apiLink);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                sb.append(inputLine);

            }

            //SAVE THE JSON FILE
            try {

                FileWriter file = new FileWriter("test.json");
                file.write(sb.toString());
                file.flush();
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            JSONArray array = new JSONArray(sb.toString());
            result.add("HEADER: " + array.getJSONObject(0).toString());
            JSONArray array1 = array.getJSONArray(1);
            for (int i = 0; i < array1.length(); ++i) {
                result.add("OBJECT NO : " + i + " " + array1.getJSONObject(i).toString());
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Could not connect");
        }
        return result;
    }*/
}
