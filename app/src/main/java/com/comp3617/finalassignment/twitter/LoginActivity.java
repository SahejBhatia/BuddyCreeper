package com.comp3617.finalassignment.twitter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    SharedPreferences shared;
    TwitterSession session;
    Result<TwitterSession> twitterresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        shared = getSharedPreferences("demotwitter", Context.MODE_PRIVATE);
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for
                // making API calls
                session = Twitter.getSessionManager()
                        .getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

//Here we get all the details of user's twitter account
                twitterresult = result;

                System.out.println(result.data.getUserName()
                        + result.data.getUserId());
                Log.d("LOGIN RESULT", "success: "+ result.data.getUserName()+ result.data.getUserId());

                MyTwitterApiClient myTwitterApiClient = new MyTwitterApiClient(session);
                Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false);
                    new Callback<User>() {

                        @Override
                        public void success(Result<User> userResult) {

                            User user = userResult.data;
//Here we get image url which can be used to set as image wherever required.      
                            Log.d("imagheurl", "success: "+user.profileImageUrl);
                        }

                        @Override
                        public void failure(TwitterException e) {

                        }

                    };

                shared.edit().putString("tweetToken", token).commit();
                shared.edit().putString("tweetSecret", secret).commit();


               //REQUESTING EMAIL

                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        // Do something with the result, which provides the
                        // email address
                        System.out.println(result.toString());
                        Log.d("Result", result.toString());
                        Toast.makeText(getApplicationContext(), result.data,
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                        System.out.println(exception.getMessage());
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });



        MyTwitterApiClient apiclients=new MyTwitterApiClient(session);

        apiclients.getUsersService().show(twitterresult.data.getUserId(), new Callback<Response>() {

            @Override
            public void failure(TwitterException arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void success(Result<Response> arg0) {
                // TODO Auto-generated method stub
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {

                    reader = new BufferedReader(new StringReader(arg0.data.body().toString()));

                    String line;

                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                String result = sb.toString();
                System.out.println("Response is>>>>>>>>>"+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    JSONArray ids=obj.getJSONArray("ids");
                    for(int i=0;i<ids.length();i++){
                        System.out.println("Id of user "+(i+1)+" is "+ids.get(i));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });

        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}