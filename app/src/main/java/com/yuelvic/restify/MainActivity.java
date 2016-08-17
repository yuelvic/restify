package com.yuelvic.restify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuelvic.rdroid.Restify;
import com.yuelvic.rdroid.util.RestObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RestObject restObject;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);

        Restify.initialize("http://your.api.domain/api/1/");

        RestObject restObject = new RestObject();
        restObject.setEndpoint("movies");
        restObject.addHeader("Content-Type", "application/json");
        restObject.addConstraint("limit", 10);
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
                textView.setText(new Gson().toJson(result));
            }
        });
    }

}
