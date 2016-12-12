package news_feed;

import java.util.Date;

/**
 * Class that represents a single news article obtained from the BBC RSS feed.
 * Stores the title, link, description, publish date and the url to the main image.
 */
public class NewsArticle {
    //Strings storing title, link, description and image of this news article
    private String title, link, description, imgURL;
    //The publish date of the article as a Date object
    private Date publishDate;

    /**
     * Constructor for a News Article
     * @param titleIn The title of the article
     * @param linkIn The url of the article
     * @param descriptionIn A short description of the article
     * @param publishDateIn Date the article was published
     * @param imgURLIn The url to the centre image for this article
     */
    public NewsArticle(String titleIn, String linkIn, String descriptionIn, Date publishDateIn, String imgURLIn) {
        //Initialise fields
        title = titleIn;
        link = linkIn;
        description = descriptionIn;
        publishDate = publishDateIn;
        imgURL = imgURLIn;
    }

    /**
     * Retrieve the title of the article
     * @return Article title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieve the url to the article
     * @return Article url
     */
    public String getLink() {
        return link;
    }

    /**
     * Retrieve a short description of what the article is about
     * @return Short description of this article
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieve the date this article was published
     * @return Article publish date as a String
     */
    public String getPublishDate() {
        //Converts and returns publish date as a String
        return String.valueOf(publishDate);
    }

    /**
     * Retrieve url to the main image of the article
     * @return Article image url as a String
     */
    public String getImgURL() {
        return imgURL;
    }
}
