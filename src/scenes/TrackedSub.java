package scenes;

import javafx.beans.property.*;
import javafx.beans.value.ObservableLongValue;
import org.jsoup.nodes.Document;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * object class for channels that are currently being tracked by socialblade
 */
public class TrackedSub {

    private final SimpleStringProperty channelID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty country;
    private final SimpleStringProperty genre;
    private final SimpleIntegerProperty subCount;
    private final SimpleLongProperty viewCount;
    private final SimpleDoubleProperty minInc;
    private final SimpleDoubleProperty maxInc;
    private String tableRow;
    private Document channelData;
    private Date dateC;

    //private final SimpleStringProperty
    private SimpleStringProperty dateC_string;

    /**
     * recieves the channelID, title, and the jsoup document for youtube subscriptions page
     * constructor
     *
     * @param channelID the base64 channel ID found in youtube URL
     * @param name      title of channel
     * @param cData     document containing the channel's socialblade page
     */
    public TrackedSub(String channelID, String name, Document cData) {
        this.channelID = new SimpleStringProperty(channelID);
        this.name = new SimpleStringProperty(name);
        channelData = cData;

        country = new SimpleStringProperty(channelData.select("#youtube-user-page-country").text());
        subCount = new SimpleIntegerProperty(Integer.parseInt(channelData.select("#youtube-stats-header-subs").text()));
        viewCount = new SimpleLongProperty(Long.parseLong(channelData.select("#youtube-stats-header-views").text()));
        genre = new SimpleStringProperty(channelData.select("#youtube-stats-header-channeltype").text());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateC = formatDate(channelData);

        dateC_string = new SimpleStringProperty(df.format(dateC).toString());

        String minMax = channelData.select("body > div:nth-child(18) > div:nth-child(4) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > p:nth-child(1)").text();

        String min = minMax.substring(1, minMax.indexOf('-') - 1);
        String max = minMax.substring(minMax.lastIndexOf('$') + 1);

        minInc = calcMoney(min);
        maxInc = calcMoney(max);

        tableRow = makeTableRow();
    } // end TrackedSub constructor


    public TrackedSub(String channelID, String name, String country, String genre, int subCount, long viewCount, double minInc, double maxInc, String dateC_string)
    {
        this.channelID = new SimpleStringProperty(channelID);
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
        this.genre = new SimpleStringProperty(genre);
        this.subCount = new SimpleIntegerProperty(subCount);
        this.viewCount = new SimpleLongProperty(viewCount);
        this.minInc = new SimpleDoubleProperty(minInc);
        this.maxInc = new SimpleDoubleProperty(maxInc);
        this.dateC_string = new SimpleStringProperty(dateC_string);
    }

    /**
     * finds the date the channel was created in the document (as a string) and converts the string to a Date object
     *
     * @param channelData the document for the scraped socialblade page
     * @return a date object for the date the channel was created
     */
    private Date formatDate(Document channelData) {
        String dateUF = channelData.select("#YouTubeUserTopInfoBlock > div.YouTubeUserTopInfo > span:contains(User Created) ~ span[style]").text();

        if (dateUF.length() == 13) {
            dateUF = dateUF.substring(0, 4) + "0" + dateUF.substring(4);
        }

        dateUF = dateUF.substring(0, 6) + dateUF.substring(9);

        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");

        Date date = null;
        try {
            date = df.parse(dateUF);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (date);
    }//end formatDate

    /**
     * converts string to double, and multiplies by the appropriate number based on a following character
     *
     * @param unF unformatted string
     * @return number of dollars as a double
     */
    private SimpleDoubleProperty calcMoney(String unF) {
        double dMoney;

        switch (unF.substring(unF.length() - 1)) {
            case "K":
                unF = unF.substring(0, unF.length() - 1);
                dMoney = Double.parseDouble(unF) * 1000;
                break;
            case "M":
                unF = unF.substring(0, unF.length() - 1);
                dMoney = Double.parseDouble(unF) * 1000000;
                break;
            default:
                dMoney = Double.parseDouble(unF);
                break;
        }

        return (new SimpleDoubleProperty(dMoney));
    }//end calcMoney

    public SimpleStringProperty getChannelID() {
        return channelID;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleStringProperty getCountry() {
        return country;
    }

    public SimpleStringProperty getGenre() {
        return genre;
    }

    public Date getDateC() {
        return dateC;
    }

    public SimpleStringProperty getDateC_string() {
        return dateC_string;
    }

    public SimpleIntegerProperty getSubCount() {
        return subCount;
    }

    public SimpleLongProperty getViewCount() {
        return viewCount;
    }

    public SimpleDoubleProperty getMinInc() {
        return minInc;
    }

    public SimpleDoubleProperty getMaxInc() {
        return maxInc;
    }

    public String getTableRow() {
        return tableRow;
    }

    /**
     * creates and returns a string formatted to be used in an sql statement to add a row to the table in the database
     *
     * @return table row string
     */
    private String makeTableRow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return ("('" + name.getValue() + "', " +
                "'" + channelID.getValue().substring(9) + "', " +
                "'" + country.getValue() + "', " +
                "'" + genre.getValue() + "', " +
                "'" + df.format(dateC) + "', " +
                subCount.intValue() + ", " +
                viewCount.longValue() + ", " +
                minInc.doubleValue() + ", " + maxInc.doubleValue() + ")");
    }// end makeTableRow
}
