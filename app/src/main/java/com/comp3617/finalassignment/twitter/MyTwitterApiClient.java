package com.comp3617.finalassignment.twitter;

/**
 * Created by Sahej Bhatia on 11/25/2016.
 */

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class MyTwitterApiClient extends TwitterApiClient {

    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public UsersService getUsersService() {
        return getService(UsersService.class);
    }
}

// example users/show service endpoint
interface UsersService {
    @GET("1.1/users/show.json")
    void show(@Query("user_id") Long userId,
              Callback<User> cb);

    @GET("/1.1/friends/ids.json")
    Call<List<JSONObject>> getFollowerCount(@Query("user_id")Long id,
                                            @Query("screen_name")String screenname,
                                            @Query("cursor")Long cursor,
                                            @Query("stringify_ids")Boolean sids,
                                            @Query("count")Long count);


    @GET("1.1/friends/list.json")
    Call<List<User>> getFollowersList(@Query("user_id") Long id,
                                            @Query("screen_name") String screenName,
                                            @Query("cursor")Long cursor,
                                            @Query("count")Long count,
                                            @Query("skip_status") Boolean status,
                                            @Query("include_user_entities") Boolean include_entities
    );


}




