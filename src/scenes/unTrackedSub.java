package scenes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * unfinished, selecting certain elements on youtube pages is stupidly difficult
 */
public class unTrackedSub {

    private String channelURL;
    private String name;
    Document channelData;

    public unTrackedSub(String channelURL) {
        this.channelURL = channelURL;
    }

    public String getChannelID(){
        return(channelURL.substring(9));
    }
}
