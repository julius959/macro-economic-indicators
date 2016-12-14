package api_model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class APIDataTestlite {
    ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD"));
    @Test
    /**
     * Pings the server for getResponse(), test fails if there is no response from server
     */
    public void getResponse() throws Exception {
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                String generatedLink = "countries/" + Model.countries[l].getCode() + "/indicators/" + ind.get(k) + "?date=" +
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear() + ":" + Model.getInstance().timeRanges.get(ind.get(k)).getEndYear() + "&per_page=10000&format=json";
                String apiBody = "http://api.worldbank.org/";
                String result = apiBody + generatedLink;
                if (Integer.parseInt(Model.getInstance().timeRanges.get(ind.get(k)).getStartYear()) <= Integer.parseInt(Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())) {
                    try {
                    } catch (Exception e) {
                        fail("No Response from server");
                    }
                }
            }
        }
    }
    /**
     * getData() tries to get data, and fails the test if the return is null
     */
    @Test
    public void getData() throws Exception {
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertNotNull("getData generates Data", APIData.getInstance().getData(l, ind.get(k),
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
            }
        }
    }
    /**
     * Checks if data is successfully saved locally, by getting data from the local database, and comparing it with the data online
     */

    @Test
    public void saveLocally() throws Exception {
        for (int l = 0; l < 2; l++) {
            for (int k = 1; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                APIData.getInstance().saveLocally(l, ind.get(k), (Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())));
                assertEquals("Data is stored in cache and is correct when compared to online data", APIData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()), CacheData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
            }
        }
    }
    /**
     * Negative test for getResponse, if the start year is out of bounds there should be no JSON file available
     */

    @Test
    public void negativegetResponse1() throws Exception{
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                String generatedLink = "countries/" + Model.countries[l].getCode() + "/indicators/" + ind.get(k) + "?date=" +
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear() + ":" + Model.getInstance().timeRanges.get(ind.get(k)).getEndYear() + "&per_page=10000&format=json";
                String apiBody = "http://api.worldbank.org/";
                String result = apiBody + generatedLink;
                TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
                if (Integer.parseInt(Model.getInstance().timeRanges.get(ind.get(k)).getStartYear()) <= Integer.parseInt(Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())) {
                    try {
                        JSONArray array = new JSONArray(result.toString());
                        JSONArray array1 = array.getJSONArray(1);
                        for (int i = 0; i < array1.length(); ++i) {
                            JSONObject currentObject = array1.getJSONObject(i);
                            try {
                                fail("There should be no response from server");
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
    /**
     * Negative test for getResponse, if the end year is out of bounds there should be no JSON file available
     **/

    @Test
    public void negativegetResponse2() throws Exception{
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("10000");
                String generatedLink = "countries/" + Model.countries[l].getCode() + "/indicators/" + ind.get(k) + "?date=" +
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear() + ":" + Model.getInstance().timeRanges.get(ind.get(k)).getEndYear() + "&per_page=10000&format=json";
                String apiBody = "http://api.worldbank.org/";
                String result = apiBody + generatedLink;
                TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
                if (Integer.parseInt(Model.getInstance().timeRanges.get(ind.get(k)).getStartYear()) <= Integer.parseInt(Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())) {
                    try {
                        JSONArray array = new JSONArray(result.toString());
                        JSONArray array1 = array.getJSONArray(1);
                        for (int i = 0; i < array1.length(); ++i) {
                            JSONObject currentObject = array1.getJSONObject(i);
                            try {
                                fail("There should be no response from server");
                            } catch (Exception e) {
                            }

                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
    /**
     * Negative test for getData, if the start year is out of bounds there should be no JSON file available
     **/

    @Test
    public void negativegetData1() throws Exception {
        TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("-1");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertEquals("getData generates Data", APIData.getInstance().getData(l, ind.get(k),
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()), data);
            }
        }
    }

    /**
     * Negative test for getData, if the end year is out of bounds there should be no JSON file available
     **/
    @Test
    public void negativegetData2() throws Exception {
        TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("10000");
                assertEquals("getData generates Data", APIData.getInstance().getData(l, ind.get(k),
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()), data);
            }
        }
    }
}
