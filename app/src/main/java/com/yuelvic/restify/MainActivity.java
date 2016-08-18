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
                .addUrl("yts", "https://yts.ag/api/v2/")
                .addUrl("swiperx", "http://stg.api.swiperxapp.com/api/1/")
                .create();

        restObject = new RestObject.Builder()
                .useBaseUrl("yts")
                .setEndpoint("list_movies.json")
                .addConstraint("limit", 1)
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
        restObject.useBaseUrl("swiperx");
        restObject.setEndpoint("users");
        restObject.addHeader("X-Warp-API-Key", "130rfenj1389eu398uhfr3198f");
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
