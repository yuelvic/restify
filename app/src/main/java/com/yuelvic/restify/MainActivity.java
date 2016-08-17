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

        Restify.initialize("http://192.168.0.101:3000/api/1/");

        RestObject restObject = new RestObject();
        restObject.setEndpoint("classes/news");
        restObject.addHeader("X-Warp-API-Key", "130rfenj1389eu398uhfr3198f");
        restObject.addHeader("Content-Type", "application/json");
        restObject.addField("message", "Edited post");
        restObject.update(2, new Restify.Call() {
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
