package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class IndicatorTest {


    @Test
    public void getName() throws Exception {
        ArrayList<String> indi = new ArrayList<String>(Arrays.asList ("GDP","Labour","Prices","Money","Trade","Government","Poverty"));
        for(int i = 0; i < Model.getInstance().indicators.size(); i++){
            assertEquals(Model.getInstance().indicators.get(i).getName(), indi.get(i));
        }
    }



    @Test
    public void setSubIndicatorsCodes() throws Exception {
        ArrayList<String> indi = new ArrayList<>();
        indi.add("indicator");
        for (int i = 0; i < 7; i++) {
            Model.getInstance().indicators.get(i).setSubIndicatorsCodes(new String[]{"indicator"});
            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorsCodes(), indi);
        }
    }

    @Test
    public void setSubIndicatorUnits() throws Exception {
        ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
                "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
                "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS", "SI.POV.GINI"));
        ArrayList<String> indi = new ArrayList<>();
        indi.add("unit");
        for (int i = 0; i < ind.size(); i++) {
            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorUnitFromCode(ind.get(i)), "unit");
        }
    }

    @Test
    public void getSubIndicatorUnitFromCode() throws Exception {

    }

    @Test
    public void getSubIndicatorsLabels() throws Exception {
        ArrayList<ArrayList> label = new ArrayList<>();
        ArrayList<String> gdp = new ArrayList<String>(Arrays.asList("GDP","GDP per capita","GDP growth"));
        ArrayList<String> labour = new ArrayList<String>(Arrays.asList("Employment ratio", "Unemployment ratio","Total Population","Urban population "));
        ArrayList<String> prices = new ArrayList<String>(Arrays.asList("Inflation", "Consumer Prices","Cost to export","Cost to import"));
        ArrayList<String> money = new ArrayList<String>(Arrays.asList("Real interest rate","Gross savings","Expense","Tax revenue","Cash surplus/deficit"));
        ArrayList<String> trade = new ArrayList<String>(Arrays.asList("Imports of goods and services", "Exports of goods and services"));
        ArrayList<String> gov = new ArrayList<String>(Arrays.asList("Total Central government debt","Total Government expenditure on education"));
        ArrayList<String> poverty = new ArrayList<String>(Arrays.asList("GINI index"));
        label.add(gdp); label.add(labour); label.add(prices); label.add(money); label.add(trade); label.add(gov); label.add(poverty);
        for(int i = 0; i < label.size(); i++) {
            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorsLabels(), label.get(i));
        }

    }

    @Test
    public void getLabelFromCode() throws Exception {
        ArrayList<String> code = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD", "NY.GDP.MKTP.KD.ZG", "SL.EMP.TOTL.SP.ZS", "SL.UEM.TOTL.ZS", "SP.POP.TOTL", "SP.URB.TOTL.IN.ZS",
                "FP.CPI.TOTL.ZG", "FP.CPI.TOTL", "IC.EXP.COST.CD", "IC.IMP.COST.CD", "FR.INR.RINR", "NY.GNS.ICTR.ZS", "GC.XPN.TOTL.GD.ZS", "GC.TAX.TOTL.GD.ZS", "GC.BAL.CASH.GD.ZS",
                "NE.IMP.GNFS.ZS", "NE.EXP.GNFS.ZS", "GC.DOD.TOTL.GD.ZS","SE.XPD.TOTL.GD.ZS", "SI.POV.GINI"));
        ArrayList<String> label = new ArrayList<String>(Arrays.asList("GDP","GDP per capita","GDP growth", "Employment ratio", "Unemployment ratio","Total Population","Urban population ",
                "Inflation", "Consumer Prices","Cost to export","Cost to import", "Real interest rate","Gross savings","Expense","Tax revenue","Cash surplus/deficit",
                "Imports of goods and services", "Exports of goods and services", "Total Central government debt","Total Government expenditure on education", "GINI index"));
            for (int i = 0; i < code.size(); i++) {
                    assertEquals(Model.getInstance().indicators.get(i).getLabelFromCode(code.get(i)), label.get(i));
            }
    }

    @Test
    public void getCodeFromLabel() throws Exception {

    }

}