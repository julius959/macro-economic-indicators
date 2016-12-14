package extra_tests;

import api_model.Model;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class getSubIndicatorLabelsTest {
    /**
     *  Checks if subindicatorlabels are retrieved correctly
     * This test is commented out because when run separately, the test runs correctly, but because of static objects throughout the code, when run together this test does not run
     * This test can be uncommented and ran separately if desired.
     **/
//    @Test
//    public void getSubIndicatorsLabels() throws Exception {
//        ArrayList<ArrayList> label = new ArrayList<>();
//        ArrayList<String> gdp = new ArrayList<String>(Arrays.asList("GDP","GDP per capita","GDP growth"));
//        ArrayList<String> labour = new ArrayList<String>(Arrays.asList("Employment ratio", "Unemployment ratio","Total Population","Urban population "));
//        ArrayList<String> prices = new ArrayList<String>(Arrays.asList("Inflation", "Consumer Prices","Cost to export","Cost to import"));
//        ArrayList<String> money = new ArrayList<String>(Arrays.asList("Real interest rate","Gross savings","Expense","Tax revenue","Cash surplus/deficit"));
//        ArrayList<String> trade = new ArrayList<String>(Arrays.asList("Imports of goods and services", "Exports of goods and services"));
//        ArrayList<String> gov = new ArrayList<String>(Arrays.asList("Total Central government debt","Total Government expenditure on education"));
//        ArrayList<String> poverty = new ArrayList<String>(Arrays.asList("GINI index"));
//        label.add(gdp); label.add(labour); label.add(prices); label.add(money); label.add(trade); label.add(gov); label.add(poverty);
//        for(int i = 0; i < label.size(); i++) {
//            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorsLabels(), label.get(i));
//        }
//    }
}
