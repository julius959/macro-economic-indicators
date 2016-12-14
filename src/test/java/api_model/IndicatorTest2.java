package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class IndicatorTest2 {
    /**
     *  Part of the Indicator Test Checks if the subindicatorlabels can successfully be set to anything, every subindicatorlabel is set to "label" and it checks whether if the method was successful or not
     **/
    @Test
    public void setSubIndicatorsLabels() throws Exception {
        ArrayList<String> indi = new ArrayList<>();
        indi.add("label");
        for (int i = 0; i < 7; i++) {
            Model.getInstance().indicators.get(i).setSubIndicatorsLabels(new String[]{"label"});
            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorsLabels(), indi);
        }
    }
}