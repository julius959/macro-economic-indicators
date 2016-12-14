package api_model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-11-27.
 */
public class APIDataTest {
    ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
            "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
            "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS", "SI.POV.GINI"));

    @Test
    public void getResponse() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
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
                        System.out.println(result);
                        fail("No Response from server");
                    }
                }
            }
        }
    }

    @Test
    public void getData() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertNotNull("getData generates Data", APIData.getInstance().getData(l, ind.get(k),
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
            }
        }
    }
    @Test
    public void saveLocally() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 1; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                APIData.getInstance().saveLocally(l, ind.get(k), (Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())));
                assertEquals("Data is stored in cache and is correct when compared to online data", APIData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()), CacheData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
            }
        }
    }
    @Test
    public void negativegetResponse1() throws Exception{
        for (int l = 0; l < Model.countries.length; l++) {
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
    @Test
    public void negativegetResponse2() throws Exception{
        for (int l = 0; l < Model.countries.length; l++) {
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
    @Test
    public void negativegetData1() throws Exception {
        TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("-1");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertEquals("getData generates Data", APIData.getInstance().getData(l, ind.get(k),
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()), data);
            }
        }
    }
    @Test
    public void negativegetData2() throws Exception {
        TreeMap<Integer, BigDecimal> data = new TreeMap<Integer, BigDecimal>();
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("10000");
                assertEquals("getData generates Data", APIData.getInstance().getData(l, ind.get(k),
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()), data);
            }
        }
    }
}