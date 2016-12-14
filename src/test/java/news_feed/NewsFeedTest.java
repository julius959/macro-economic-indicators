package news_feed;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-13.
 */
public class NewsFeedTest {
    @Test
    /**
     *  Checks if newsfeed is actually populated, test should fail if API fails or if offline
     **/
    public void getNews() throws Exception {
        assertNotNull(NewsFeed.getNews());
    }

}