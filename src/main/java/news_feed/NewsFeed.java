import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.jdom.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*--

 $Id: LICENSE.txt,v 1.11 2004/02/06 09:32:57 jhunter Exp $

 Copyright (C) 2000-2004 Jason Hunter & Brett McLaughlin.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The name "JDOM" must not be used to endorse or promote products
    derived from this software without prior written permission.  For
    written permission, please contact <request_AT_jdom_DOT_org>.

 4. Products derived from this software may not be called "JDOM", nor
    may "JDOM" appear in their name, without prior written permission
    from the JDOM Project Management <request_AT_jdom_DOT_org>.

 In addition, we request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by the
      JDOM Project (http://www.jdom.org/)."
 Alternatively, the acknowledgment may be graphical using the logos
 available at http://www.jdom.org/images/logos.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 This software consists of voluntary contributions made by many
 individuals on behalf of the JDOM Project and was originally
 created by Jason Hunter <jhunter_AT_jdom_DOT_org> and
 Brett McLaughlin <brett_AT_jdom_DOT_org>.  For more information
 on the JDOM Project, please see <http://www.jdom.org/>.

 */
public class NewsFeed {
    //News feed
    private static SyndFeed sfFeed;

    private static void establishConnection() throws IOException, FeedException {
        //URL of BBC Economy RSS feed
        URL feedUrl = new URL("http://feeds.bbci.co.uk/news/business/economy/rss.xml");

        //Establish connection to news feed
        SyndFeedInput sfiInput = new SyndFeedInput();
        sfFeed = sfiInput.build(new XmlReader(feedUrl));
    }

    public static ArrayList<NewsArticle> getNews() throws IOException, FeedException {
        //Establish connection to feed
        establishConnection();

        //Container for articles
        ArrayList<NewsArticle> alstArticles = new ArrayList<>();

        //Collection of articles
        List lstArticles = sfFeed.getEntries();

        //Add each article to the list of articles
        for (Object item : lstArticles) {
            SyndEntry seArticle = (SyndEntry) item;

            //URL of news articles image - defaults to no logo found
            String imgURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1000px-No_image_available.svg.png";

            //Retrieves image URL from news article xml
            List<Element> foreignMarkups = (List<Element>) seArticle.getForeignMarkup();
            for (Element foreignMarkup : foreignMarkups) {
                imgURL = foreignMarkup.getAttribute("url").getValue();
            }

            alstArticles.add(new NewsArticle(seArticle.getTitle(), seArticle.getLink(), seArticle.getDescription().getValue(), seArticle.getPublishedDate(), imgURL));
        }



        return alstArticles;
    }
}
