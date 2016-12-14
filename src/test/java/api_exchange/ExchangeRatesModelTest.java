package api_exchange;

import exchange_rate.ExchangeRatesModel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-13.
 */
public class ExchangeRatesModelTest {
    @Test
    /**
     * This test checks if getData() returns null or not, when there is no internet connection, or there is something wrong with the API, the test would fail.
     */
    public void getData() throws Exception {
        for (int i = 0; i < ExchangeRatesModel.getData().size(); i++) {
            assertFalse(ExchangeRatesModel.getData().values().contains(null));
        }
    }
}
