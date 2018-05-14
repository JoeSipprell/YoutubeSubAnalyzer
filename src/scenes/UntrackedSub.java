package scenes;

import javafx.beans.property.SimpleStringProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * unfinished, selecting certain elements on youtube pages is stupidly difficult
 * and I don't have time to figure out YouTube's api
 */
public class UntrackedSub {

    private String channelURL;
    private String name;
    Document channelData;

    public UntrackedSub(String channelURL) {
        this.channelURL = channelURL;
    }

    public String getChannelID(){
        return(channelURL.substring(9));
    }
}
