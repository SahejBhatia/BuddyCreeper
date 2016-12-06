package com.comp3617.finalassignment.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3617.finalassignment.twitter.Model.CustomTweet;
import com.comp3617.finalassignment.twitter.Utils.ImageLoadTask;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Place;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class welcomeActivity extends AppCompatActivity implements GetuserNameFragment.GetuserNameListner {

    private EditText searchUser;
    private TextView welcomeUserName;
    private Button getTweets;
    private Button getFollowers;
    private String searchuser;
    private SharedPreferences shared;
    private TwitterSession session;
    Result<TwitterSession> twitterresult;
    private  String username;
    private String searchBythisName;
    private String twitterImageUrl;
    private ImageView profilePic;
    private ImageView background;
    private  Long userid;
    private final Long count = 15L;
    private JSONObject jsonObject = null;
    private ArrayList<Tweet> tweetList;
    private ArrayList<CustomTweet> customTweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetList = new ArrayList<>();
        customTweetsList = new ArrayList<>();

        TwitterCred.Initialize(this);
        setContentView(R.layout.activity_welcome);
        getUserData();
        //grab session data
        Bundle b = getIntent().getExtras();
        getTweets = (Button) findViewById(R.id.gettweetsbutton);
        getFollowers = (Button) findViewById(R.id.getFollowersbutton);
        welcomeUserName = (TextView) findViewById(R.id.welcomeUserName);
        profilePic = (ImageView) findViewById(R.id.profileimageView);

        username = TwitterCred.session.getUserName();
        userid = TwitterCred.session.getUserId();

        welcomeUserName.setText(username);

        //get tweets
        getTweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch search by name dialog fragment
                showEditDialog();
            }
        });




        //get user followers
        getFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // displayFollowers();

                // calling intent for showfollowersActivity

                Intent intent = new Intent(welcomeActivity.this, showfollowersActivity.class);
                intent.putExtra("id",userid );
                startActivity(intent);
            }
        });
    }



    private void getTweetByUserName(String searchname) {

        //get tweets and gelocations of  @user eneterd by user
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient( TwitterCred.session);
        Call<List<Tweet>> userResult = twitterApiClient.getStatusesService().userTimeline(null, searchname,50, null, null, false, false, false, true);
        userResult.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> listResult) {

                ArrayList<CustomTweet> ct = new ArrayList<CustomTweet>();


                for(Tweet tweet: listResult.data) {

                    try{
                        CustomTweet customTweet = new CustomTweet();

                        Log.d("fabricstuff", "result: " + tweet.text + "  " + tweet.createdAt+ tweet.place );

                        Place coordinates = tweet.place;

                        if (coordinates!= null){
                            customTweet.setTweetText(tweet.text);
                            customTweet.setUserName(tweet.user.name);
                            customTweet.setDatestamp(tweet.createdAt);
                            customTweet.setCoord(tweet.place);
                            customTweet.setUrl(tweet.user.profileImageUrl);
                            List<MediaEntity> media= tweet.entities.media;
                            Log.d("URL", "success: "+ media.get(0).url);
                            if(media.size() > 0){
                                customTweet.setMediaUrl(media.get(0).url);
                            }

                            //tweetList.add(tweet);
                            Log.d("cpoordomayes", "succes s: "+ coordinates.boundingBox.coordinates);
                            List<List<List<Double>>> c = coordinates.boundingBox.coordinates;
                            Log.d("coorsd", "success: "+ coordinates.toString());
                            Log.d("first list", "success: "+ c.get(0).get(0));
                            ArrayList<Double> finalcoord = getCoordinates(c);

                            //just getting the first coordiates
                            customTweet.setLongt(finalcoord.get(1));
                            customTweet.setLat(finalcoord.get(0));

                        }else{
                            TwitterCred.TweetListNoCoord.add(tweet);
                        }
                        ct.add(customTweet);
                    }catch (TwitterException e) {
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }

                }
              TwitterCred.customTweetslist = ct;

                //grab customtweetlist -> send it to getCoordinates



                //calling out the maps activity with user coordinates
                Intent myIntent = new Intent(welcomeActivity.this, MapsActivity.class);
                myIntent.putExtra("key", "5"); //Optional parameters
               // myIntent.putExtra("TweetList", tweetList);
                welcomeActivity.this.startActivity(myIntent);

            }
            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });
    }


    private  ArrayList<Double> getCoordinates(List<List<List<Double>>> c){


        Double longt, lat;
        ArrayList<Double> longarray = new ArrayList<>();
        ArrayList<Double> latarray = new ArrayList<>();
        ArrayList<Double> sendback = new ArrayList<>();

        for(int i = 0; i< c.size(); i++){
            for(int j=0; j<c.get(i).size();j++){
              longt =  c.get(i).get(j).get(0);
                lat = c.get(i).get(j).get(1);
                longarray.add(longt);
                latarray.add(lat);
            }
        }
        Collections.sort(longarray);
        Collections.sort(latarray);
        Double finalLat =  latarray.get(0) + ( latarray.get(latarray.size()-1) - latarray.get(0))/2 ;
        Double finallong = longarray.get(0) + ( longarray.get(longarray.size()-1) - longarray.get(0))/2 ;

        Log.d("Lat", "getCoordinates: " + finalLat);
        Log.d("Long", "getCoordinates: " + finallong);

        sendback.add(finalLat);
        sendback.add(finallong);
        return sendback;

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        GetuserNameFragment getuserNameFragment = GetuserNameFragment.newInstance("Search");
        getuserNameFragment.show(fm , "fragment_getuser_name");
    }

    //get searchname back from GetUserNameFragment
    //launch search method

    @Override
    public void onFinishEditDialog(String inputText) {
        Log.d("input", "onFinishEditDialog: " + inputText);
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
        searchBythisName = inputText;
        getTweetByUserName(searchBythisName);
    }


    void getUserData() {
        Call<User> userResult = Twitter.getApiClient(TwitterCred.session).getAccountService().verifyCredentials(true,false);
        userResult.enqueue(new Callback<User>() {

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();           }

            @Override
            public void success(Result<User> userResult) {

                User user = userResult.data;
                String twitterImageUrl = user.profileImageUrl.replace("_normal", "_bigger");;
                new ImageLoadTask(twitterImageUrl, profilePic).execute();

                try {
                    Log.d("imageurl", user.profileBannerUrl);
                    Log.d("name", user.name);
                    Log.d("des", user.description);
                    Log.d("followers ", String.valueOf(user.followersCount));
                    Log.d("createdAt", user.createdAt);
                    Log.d("LOCATION", "success: " + user.location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
