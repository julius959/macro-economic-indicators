package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class CacheDataTestlite {
    ArrayList<String> ind = new ArrayList<String>(Arrays.asList("NY.GDP.MKTP.CD", "NY.GDP.PCAP.CD"));

    /**
     * Test for getData for CacheData, saves every single data to the local database, and compares it to the data online to see if data is retrieved correctly
     **/
    @Test
    public void getData() throws Exception {
        for (int l = 0; l < 2; l++) {
            for (int k = 1; k < ind.size(); k++) {
                Model.getInstance().timeRanges.get(ind.get(k)).setStartYear("0000");
                Model.getInstance().timeRanges.get(ind.get(k)).setEndYear("9999");
                APIData.getInstance().saveLocally(l, ind.get(k), (Model.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())));
                assertEquals(CacheData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())
                        ,(APIData.getInstance().getData(l, ind.get(k), Model.getInstance().timeRanges.get(ind.get(k)).getStartYear(), Model.getInstance().timeRanges.get(ind.get(k)).getEndYear())));
            }
        }
    }
}
