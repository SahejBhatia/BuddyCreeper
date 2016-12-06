package com.comp3617.finalassignment.twitter.Model;

import com.twitter.sdk.android.core.models.Place;

/**
 * Created by Sahej Bhatia on 12/1/2016.
 */
public class CustomTweet {

    public CustomTweet(){}


    private String tweetText;
    private String userName;
    private String datestamp;
    private Place coord;
    private double longt;
    private double lat;

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    private String mediaUrl;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }



    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public Place getCoord() {
        return coord;
    }

    public void setCoord(Place coord) {
        this.coord = coord;
    }


}
