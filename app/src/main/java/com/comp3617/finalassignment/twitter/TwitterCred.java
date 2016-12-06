package com.comp3617.finalassignment.twitter;

import android.content.Context;

import com.comp3617.finalassignment.twitter.Model.CustomTweet;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Sahej Bhatia on 11/27/2016.
 */
public class TwitterCred {

    public static  TwitterSession session;
    private static TwitterCred INSTANCE;
    public List<List<List<Double>>> TwitterCoordinates;
    private static final String TWITTER_KEY = "aKDHol41HjiJHau9r7sgNlWDs";
    private static final String TWITTER_SECRET = "bcGh3utIMyizUZT3EFf4BeEgMx121bLhSAToN92ZByrSnz8FJT";

    public static ArrayList<Tweet> TweetList;
    public static ArrayList<Tweet> TweetListNoCoord;
    public static ArrayList<CustomTweet> customTweetslist;


    public static TwitterCred getInstance(Context ctx) {

        if (INSTANCE == null)
            INSTANCE = new TwitterCred();

        return INSTANCE;
    }

    private TwitterCred() {
        TweetList = new ArrayList<>();
        TweetListNoCoord = new ArrayList<>();
        customTweetslist = new ArrayList<>();

    }


    public static void Initialize(Context context){

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new Twitter(authConfig), new Crashlytics());
    }
}
