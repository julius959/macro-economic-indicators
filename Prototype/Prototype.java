


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Prototype {
    private static String apiLink = "http://api.worldbank.org/countries/all/indicators/SP.POP.TOTL?format=json";


    public static void main(String[] args) {
        System.out.println(getResponse(apiLink));

    }
    public static ArrayList<String> getResponse(String apiLink) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(apiLink);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                sb.append(inputLine);

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
    }
}
