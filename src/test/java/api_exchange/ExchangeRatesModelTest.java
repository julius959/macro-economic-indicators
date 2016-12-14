package api_exchange;

import exchange_rate.ExchangeRatesModel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-13.
 */
public class ExchangeRatesModelTest {
    @Test
    public void getData() throws Exception {
        for (int i = 0; i < ExchangeRatesModel.getData().size(); i++) {
            assertFalse(ExchangeRatesModel.getData().values().contains(null));
        }
    }
}
