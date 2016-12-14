package api_model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-14.
 */
public class IndicatorTest {
    /**
     *  Checks if name of the indicator (parent) is retrieved correctly by comparing with the ArrayList "indi"
     **/

    @Test
    public void getName() throws Exception {
        ArrayList<String> indi = new ArrayList<String>(Arrays.asList ("GDP","Labour","Prices","Money","Trade","Government","Poverty"));
        for(int i = 0; i < Model.getInstance().indicators.size(); i++){
            assertEquals(Model.getInstance().indicators.get(i).getName(), indi.get(i));
        }
    }

    /**
     *  Checks if the subindicatorcodes can successfully be set to anything, every subindicator is set to "indicator" and it checks whether if the method was successful or not
     **/
    @Test
    public void setSubIndicatorsCodes() throws Exception {
        ArrayList<String> indi = new ArrayList<>();
        indi.add("indicator");
        for (int i = 0; i < 7; i++) {
            Model.getInstance().indicators.get(i).setSubIndicatorsCodes(new String[]{"indicator"});
            assertEquals(Model.getInstance().indicators.get(i).getSubIndicatorsCodes(), indi);
        }
    }

}