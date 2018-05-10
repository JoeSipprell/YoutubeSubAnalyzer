package scenes;

import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * object class for channels that are currently being tracked by socialblade
 */
public class trackedSub {

    private final String channelID;
    private final String name;
    private final String country;
    private final String genre;
    private Date dateC;
    private final Integer subCount;
    private final long viewCount;
    private final double minInc;
    private final double maxInc;

    private final Document channelData;

    /**
     *
     * @param channelID the base64 channel ID found in youtube URL
     * @param name title of channel
     * @param cData document containing the channel's socialblade page
     */
    public trackedSub(String channelID, String name, Document cData){
        this.channelID = channelID;
        this.name = name;
        channelData = cData;

        country = channelData.select("#youtube-user-page-country").text();
        subCount = Integer.parseInt(channelData.select("#youtube-stats-header-subs").text());
        viewCount = Long.parseLong(channelData.select("#youtube-stats-header-views").text());
        genre = channelData.select("#youtube-stats-header-channeltype").text();
        dateC = formatDate(channelData);

        String minMax = channelData.select("body > div:nth-child(18) > div:nth-child(4) > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > p:nth-child(1)").text();

        String min = minMax.substring(1,minMax.indexOf('-') - 1);
        String max = minMax.substring(minMax.lastIndexOf('$') + 1);

        minInc = calcMoney(min);
        System.out.println(minInc);
        maxInc = calcMoney(max);
        System.out.println(maxInc);

        System.out.println(this.name);
    } // end trackedSub constructor

    /**
     * finds the date the channel was created in the document (as a string) and converts the string to a Date object
     * @param channelData the document for the scraped socialblade page
     * @return a date object for the date the channel was created
     */
    private Date formatDate(Document channelData){
        String dateUF = channelData.select("#YouTubeUserTopInfoBlock > div.YouTubeUserTopInfo > span:contains(User Created) ~ span[style]").text();

        if(dateUF.length() == 13){
            dateUF = dateUF.substring(0,4) + "0" + dateUF.substring(4);
        }

        dateUF = dateUF.substring(0,6) + dateUF.substring(9);

        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");

        Date date = null;
        try {
            date = df.parse(dateUF);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return(date);
    }//end formatDate

    private double calcMoney(String unF){
        double dMoney = 0;

        switch(unF.substring(unF.length() - 1)){
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

        return(dMoney);
    }

}
