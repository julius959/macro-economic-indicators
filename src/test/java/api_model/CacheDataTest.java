package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-11-27.
 */
public class CacheDataTest {
    //Checks if cached data matches online data
    //Last updated 12-12-16
    @Test
    public void getData() throws Exception {
        ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
                "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
                "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "SI.POV.GINI"));
        for (int l = 0; l < Model.countries.length; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertEquals(Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())
                        ,(APIData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())));
            }
        }
    }
}