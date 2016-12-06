package com.comp3617.finalassignment.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.comp3617.finalassignment.twitter.Model.UserAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.SafeListAdapter;
import com.twitter.sdk.android.core.models.SafeMapAdapter;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.Response.success;

public class showfollowersActivity extends AppCompatActivity {

    private ListView tasklistview;
    private UserAdapter adapter;
    private ArrayList<User> myUsers ;
    private LinearLayout empty;
    private Long userid;
    private JSONArray ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfollowers);


        tasklistview = (ListView) findViewById(R.id.taskListView);
        tasklistview.setEmptyView(findViewById(android.R.id.empty));
        empty = (LinearLayout) findViewById(R.id.empty);

        Bundle extras = getIntent().getExtras();
        userid = extras.getLong("id");

        bindTasks();
        //on clicking -> direct tyo maops intent !!!
        tasklistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //PASS THE USER ID AND ITS DETAILS TO MAPS ACIVITY TO DISPLAY ON MAP
               /* ArrayAdapter<Task> taskAdapter = (TaskAdapter) tasklistview.getAdapter();
                Task thistask = taskAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, TaskDetails.class);
                intent.putExtra("com.comp3617.assignment2.sahejbhatia", thistask);
                intent.putExtra("position", position);
                startActivityForResult(intent, 100);

                 //calling out the maps activity with user coordinates
                Intent myIntent = new Intent(welcomeActivity.this, MapsActivity.class);
                myIntent.putExtra("key", "5"); //Optional parameters
                welcomeActivity.this.startActivity(myIntent);

                */

            }
        });

        // taskListSingleton = TaskListSingleton.getInstance(this);
        bindTasks();
    }

    private void bindTasks() {

        final Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new SafeListAdapter())
                .registerTypeAdapterFactory(new SafeMapAdapter())
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersService service = retrofit.create(UsersService.class);

        Call<List<User>> repos = service.getFollowersList(userid, null, null, null ,null,null);
        repos.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                success(new Result<>(response.body(), response));
                String jsonString = response.body().toString();
                Log.i("onResponse", jsonString);
                Type listType = new TypeToken<List<MediaEntity.Size>>() {}.getType();
                List<MediaEntity.Size> yourList = new Gson().fromJson(jsonString, listType);
                Log.i("onResponse", yourList.toString());

                Log.d("size", "onResponse: "+ response.isSuccessful());

                if(response.isSuccessful()){

                    success(new Result<>(response.body(), response));
                    Log.d("RESPONSE", "onResponse: "+ response.body().toString());

                    Iterator i = response.body().iterator();
                    while(i.hasNext()){
                        Log.d("ITERATOR", "onResponse: "+ i.hasNext());


                    }



                }
               // Log.d("TOSTRING", "onResponse: " + response.body().toString());



            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


       // SafeListAdapter safeListAdapter = safeListAdapter.create() ;


        empty.setVisibility(View.INVISIBLE);

             //myUsers =

       // adapter = new UserAdapter(this, myUsers);
       // tasklistview.setAdapter(adapter);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if ((requestCode == 100) && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
           /* Task updatedTask = extras.getParcelable("com.comp3617.assignment2.sahejbhatia");
            String flag = extras.getString("flag");

            if(flag.equalsIgnoreCase("save")){
                int pos = extras.getInt("position");
                taskListSingleton.onUpdate(pos, updatedTask);
            }else if (flag.equalsIgnoreCase("delete")){
                int pos = extras.getInt("position");
                taskListSingleton.onDelete(pos);
            }else if(flag.equalsIgnoreCase("create")){
                taskListSingleton.onCreate(updatedTask);
            }*/

            bindTasks();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
