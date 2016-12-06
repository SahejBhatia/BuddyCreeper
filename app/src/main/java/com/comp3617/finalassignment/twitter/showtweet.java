package com.comp3617.finalassignment.twitter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class showtweet extends Fragment {

    private showtweetListner listener;
    private ImageView imageView;
    private TextView textView;

    private WebView webView;

    public showtweet() {
        // Required empty public constructor
    }


    public interface showtweetListner {
        void onFinishEditDialog(String searchUsername);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=  inflater.inflate(R.layout.fragment_showtweet, container, false);
        imageView = (ImageView)view.findViewById(R.id.mediaImage);
        textView = (TextView) view.findViewById(R.id.tweetext);
        webView = (WebView)view.findViewById(R.id.webView);
        Bundle bundle = this.getArguments();
        String text = bundle.getString("title");
        String url = bundle.getString("url");
        textView.setText(text);
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        return view;

    }

    public static showtweet newInstance(String title){
        showtweet nameFragment= new showtweet();
        Bundle  args = new Bundle();
        args.putString("title",title);
        nameFragment.setArguments(args);
        return nameFragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
    }

}
