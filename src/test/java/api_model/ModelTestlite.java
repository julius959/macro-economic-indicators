package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class ModelTestlite {

    ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD"));
    /**
     *  Checks if data is successfully gather or not by asserting false if the return is empty
     **/
    @Test
    public void gatherData() throws Exception {
        ArrayList<Integer> countries = new ArrayList<>();
        for (int i = 0; i < 43; i++) {
            countries.add(i);
            Model.currentCountries.add(i);
        }
        for (int k = 0; k < ind.size(); k++) {
            assertFalse(Model.getInstance().gatherData(countries, ind.get(k)).isEmpty());
        }
    }

    @Test
    /**
     *  Tests if the regular expression for removing "." works properly for purposes for storing data to the SQLlite database
     **/
    public void eraseDots() throws Exception {
        for(int i = 0; i < ind.size(); i++) {
            assertFalse(Model.getInstance().eraseDots(ind.get(i)).matches("\\."));
        }
    }

    /**
     *  Checks if the getData method successfully retrieves data from both online and offline database. The test passes if data is returned
     **/
    @Test
    public void getData() throws Exception {
        for (int l = 0; l < 2; l++) {
            for (int k = 0; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                assertNotNull(Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear()));
            }
        }
    }
}
