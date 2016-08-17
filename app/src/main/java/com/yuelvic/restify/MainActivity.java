package com.yuelvic.restify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.yuelvic.rdroid.Restify;
import com.yuelvic.rdroid.util.RestObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Restify.initialize("http://stg.api.swiperxapp.com/api/1/");

        RestObject restObject = new RestObject.Builder()
                .setEndpoint("users")
                .addHeader("X-Warp-API-Key", "130rfenj1389eu398uhfr3198f")
                .create();

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
                Log.d("RESULT", new Gson().toJson(result));
            }
        });
    }
}
