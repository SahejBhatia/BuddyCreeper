package com.comp3617.finalassignment.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {


    private TwitterLoginButton loginButton;

    private static final String TWITTER_KEY = "aKDHol41HjiJHau9r7sgNlWDs";
    private static final String TWITTER_SECRET = "bcGh3utIMyizUZT3EFf4BeEgMx121bLhSAToN92ZByrSnz8FJT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        TwitterCred.Initialize(this);

       // TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
       // Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        setContentView(R.layout.activity_main);
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

        //login button action


        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(final Result<TwitterSession> result) {

                TwitterCred.session = result.data;

                Intent myIntent = new Intent(MainActivity.this, welcomeActivity.class);

                MainActivity.this.startActivity(myIntent);

            }

            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
