package com.comp3617.finalassignment.twitter.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.comp3617.finalassignment.twitter.R;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by Sahej Bhatia on 11/30/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {


    private final Context context;
    private List<User> users;

    public UserAdapter(Context c, List<User> t){
        super(c, R.layout.row, t);
        context = c;
        this.users = t;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (convertView != null)
            view = convertView;
        else
            view = inflater.inflate(R.layout.row,  parent, false);


/*
        User task = User.get(position);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        ImageView imageView = (ImageView) view.findViewById(R.id.priorityImage);

        tvTitle.setText(task.getTitle());
        tvBody.setText(task.getDescription());
        task.setPriority(TaskListSingleton.status);
        if(task.getPriority().equals("Low")){
            imageView.setImageResource(R.mipmap.row_image);

        }else if(task.getPriority().equals("Medium")){
            imageView.setImageResource(R.mipmap.priority_med);

        }else if(task.getPriority().equals("High")){
            imageView.setImageResource(R.mipmap.priority_high);
        }*/

        return view;
    }
}
