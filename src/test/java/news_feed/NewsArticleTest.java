package news_feed;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-13.
 */
public class NewsArticleTest {
    /**
     *  Checks if newsarticle successfully retrieves its title
     **/

    @Test
    public void getTitle() throws Exception {
        NewsFeed.getNews();
        for (int i = 0; i < NewsFeed.getNews().size(); i++) {
            assertNotNull(NewsFeed.getNews().get(i).getTitle());
        }
    }
    /**
     *  Checks if newsarticle successfully retrieves its link
     **/
    @Test
    public void getLink() throws Exception {
        NewsFeed.getNews();
        for (int i = 0; i < NewsFeed.getNews().size(); i++) {
            assertNotNull(NewsFeed.getNews().get(i).getLink());
        }
    }

    @Test
    public void getDescription() throws Exception {
        NewsFeed.getNews();
        for (int i = 0; i < NewsFeed.getNews().size(); i++) {
            assertNotNull(NewsFeed.getNews().get(i).getDescription());
        }
    }
    /**
     *  Checks if newsarticle successfully retrieves its publish date
     **/
    @Test
    public void getPublishDate() throws Exception {
        NewsFeed.getNews();
        for (int i = 0; i < NewsFeed.getNews().size(); i++) {
        assertNotNull(NewsFeed.getNews().get(i).getPublishDate());
        }
    }
    /**
     *  Checks if newsarticle successfully retrieves its image url
     **/
    @Test
    public void getImgURL() throws Exception {
        NewsFeed.getNews();
        for (int i = 0; i < NewsFeed.getNews().size(); i++) {
        assertNotNull(NewsFeed.getNews().get(i).getImgURL());
        }
    }

}