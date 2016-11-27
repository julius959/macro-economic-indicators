import java.util.Date;

public class NewsArticle {
    private String title, link, description;
    private Date publishDate;

    public NewsArticle(String titleIn, String linkIn, String descriptionIn, Date publishDateIn) {
        title = titleIn;
        link = linkIn;
        description = descriptionIn;
        publishDate = publishDateIn;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishDate() {
        return String.valueOf(
                publishDate);
    }
}
