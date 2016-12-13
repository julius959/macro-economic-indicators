package api_model;

import org.junit.Test;

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
            "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "SI.POV.GINI"));

    @Test
    public void getResponse() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertNotNull("URL gets response", APIData.getInstance().getResponse("countries/" + Model.countries[l].getCode() + "/indicators/" + ind.get(k) + "?date=" +
                        Model.getInstance().timeRanges.get(ind.get(k)).getStartYear() + ":" + Model.getInstance().timeRanges.get(ind.get(k)).getEndYear() + "&per_page=10000&format=json"));
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
            for (int k = 0; k < ind.size(); k++) {
                //Store all data possible to local DB
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                APIData.getInstance().saveLocally(l, ind.get(k), (Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())));
                assertNotNull("Data is stored in cache and retreived successfully", CacheData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
            }
        }
    }
}

