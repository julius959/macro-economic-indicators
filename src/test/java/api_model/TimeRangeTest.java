package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-13.
 */
public class TimeRangeTest {
    ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
            "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
            "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS", "SI.POV.GINI"));
    @Test
    /**
     *  Checks if the startyear for indicator is correctly changed when setStartYear method is used
     **/

    public void setStartYear() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 1; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("2000");
                assertEquals( Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), "2000");
            }
        }
    }
    /**
     *  Checks if the endyear for indicator is correctly changed when setEndYear method is used
     **/
    @Test
    public void setEndYear() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 1; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("2000");
                assertEquals( Model.getInstance().timeRanges.get(ind.get(k)).getEndYear(), "2000");
            }
        }
    }

    /**
     *  Checks if the startyear for indicator is correctly retrieved
     **/
    @Test
    public void getStartYear() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 1; k < ind.size(); k++) {
                assertTrue( Model.getInstance().timeRanges.get(ind.get(k)).getStartYear().matches("\\d\\d\\d\\d"));
            }
        }
    }
    /**
     *  Checks if the endyear for indicator is correctly retrieved
     **/

    @Test
    public void getEndYear() throws Exception {
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 1; k < ind.size(); k++) {
                assertTrue( Model.getInstance().timeRanges.get(ind.get(k)).getEndYear().matches("\\d\\d\\d\\d"));
            }
        }
    }

}