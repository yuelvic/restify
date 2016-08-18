package com.yuelvic.restify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuelvic.rdroid.Restify;
import com.yuelvic.rdroid.util.RestObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RestObject restObject;
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);

        new Restify.Builder()
                .addUrl("movie", "http://api.movielibrary.com/api/v1")
                .addUrl("music", "http://api.musicboom.com/api/v2")
                .create();

        restObject = new RestObject.Builder()
                .useBaseUrl("movie")
                .setEndpoint("list_movies.json")
                .addConstraint("limit", 10)
                .findAll(new Restify.Call() {
                    @Override
                    public void onCompleted() {
                        fetchFromDiffUrl();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(HashMap<String, Object> result) {
                        textView2.setText(new Gson().toJson(result));
                    }
                });
    }

    private void fetchFromDiffUrl() {
        restObject.useBaseUrl("music");
        restObject.setEndpoint("music_list");
        restObject.addHeader("API-KEY", "68fsduofd8s7f6t7sd");
        restObject.findAll(new Restify.Call() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(HashMap<String, Object> result) {
                textView1.setText(new Gson().toJson(result));
            }
        });
    }

}
