package extra_tests;

import api_model.Model;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-14.
 * This test is commented out because when run separately, the test runs correctly, but because of static objects throughout the code, when run together this test does not run
 * This test can be uncommented and ran separately if desired.
 */
public class getCodeFromLabelTest {
//
//    /**
//     *  Part of the IndicatorTest, checks if code can be obtained with the label, it compares the code with the array "label" for each sublabel
//     **/
//
//    @Test
//    public void getCodeFromLabel() throws Exception {
//        ArrayList<String> code = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
//                "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
//                "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS", "SI.POV.GINI"));
//        ArrayList<String> label = new ArrayList<String>(Arrays.asList("GDP","GDP per capita","GDP growth", "Employment ratio", "Unemployment ratio","Total Population","Urban population ",
//                "Inflation", "Consumer Prices","Cost to export","Cost to import", "Real interest rate","Gross savings","Expense","Tax revenue","Cash surplus/deficit",
//                "Imports of goods and services", "Exports of goods and services", "Total Central government debt","Total Government expenditure on education", "GINI index"));
//        for (int i = 0; i < code.size(); i++) {
//            int k = 0;
//            while (i < code.size()) {
//                try {
//                    Assert.assertEquals(Model.getInstance().indicators.get(k).getCodeFromLabel(label.get(i)), code.get(i));
//                    i++;
//                } catch (Exception e) {
//                    k++;
//                    assertEquals(Model.getInstance().indicators.get(k).getCodeFromLabel(label.get(i)), code.get(i));
//                }
//            }
//        }
//    }
}