package api_model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-11-27.
 * This test is no longer used because it would take too long when it is built by gradle.
 * The reason it takes too long was because it went through every single country and indicator and year, generating over 40000 results and checking every single one of them
 * However, this test has been included so if you are interested in extensive testing, you could uncomment the class and test it yourself
 * The simpler version of this test is ModelTestlite
 */
public class ModelTest {
//
//    ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
//            "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
//            "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS", "SI.POV.GINI"));
//    /**
//     *  Checks if data is successfully gather or not by asserting false if the return is empty
//     **/
//    @Test
//    public void gatherData() throws Exception {
//        ArrayList<Integer> countries = new ArrayList<>();
//        for (int i = 0; i < 43; i++) {
//            countries.add(i);
//            Model.currentCountries.add(i);
//        }
//            for (int k = 0; k < ind.size(); k++) {
//                assertFalse(Model.getInstance().gatherData(countries, ind.get(k)).isEmpty());
//        }
//    }
//
//    @Test
//    /**
//     *  Tests if the regular expression for removing "." works properly for purposes for storing data to the SQLlite database
//     **/
//    public void eraseDots() throws Exception {
//        for(int i = 0; i < ind.size(); i++) {
//            assertFalse(Model.getInstance().eraseDots(ind.get(i)).matches("\\."));
//        }
//    }
//
//    /**
//     *  Checks if the getData method successfully retrieves data from both online and offline database. The test passes if data is returned
//     **/
//    @Test
//    public void getData() throws Exception {
//        for (int l = 0; l < Model.countries.length; l++) {
//            for (int k = 0; k < ind.size(); k++) {
//                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
//                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
//                assertNotNull(Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
//            }
//        }
//    }
}