package badtests;

import api_model.Model;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class getSubIndicatorsCodesTest {
    /**
     *  Part of the IndicatorTest, checks if subindicator code is successfully retrieved
     **/

    @Test
    public void getSubIndicatorsCodes() throws Exception {
        ArrayList<ArrayList> indi = new ArrayList<>();
        ArrayList<String> gdp = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD","NY.GDP.PCAP.CD","NY.GDP.MKTP.KD.ZG"));
        ArrayList<String> labour = new ArrayList<String>(Arrays.asList("SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS","SP.POP.TOTL","SP.URB.TOTL.IN.ZS"));
        ArrayList<String> prices = new ArrayList<String>(Arrays.asList("FP.CPI.TOTL.ZG", "FP.CPI.TOTL","IC.EXP.COST.CD","IC.IMP.COST.CD"));
        ArrayList<String> money = new ArrayList<String>(Arrays.asList("FR.INR.RINR","NY.GNS.ICTR.ZS","GC.XPN.TOTL.GD.ZS","GC.TAX.TOTL.GD.ZS","GC.BAL.CASH.GD.ZS"));
        ArrayList<String> trade = new ArrayList<String>(Arrays.asList("NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS"));
        ArrayList<String> gov = new ArrayList<String>(Arrays.asList("GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS"));
        ArrayList<String> poverty = new ArrayList<String>(Arrays.asList("SI.POV.GINI"));
        indi.add(gdp); indi.add(labour); indi.add(prices); indi.add(money); indi.add(trade); indi.add(gov); indi.add(poverty);
        for(int i = 0; i < indi.size(); i++) {
            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorsCodes(), indi.get(i));
        }
    }
}