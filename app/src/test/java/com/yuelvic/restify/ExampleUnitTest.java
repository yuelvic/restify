package com.yuelvic.restify;

import android.util.Log;

import com.google.gson.Gson;
import com.yuelvic.rdroid.Restify;
import com.yuelvic.rdroid.util.RestObject;
import com.yuelvic.rdroid.util.RestResult;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void read() {
        Restify.initialize("https://yts.ag/api/v2/");
        RestObject restObject = new RestObject.Builder().create();
        restObject.findAll(new Restify.Call() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(RestResult result) {
                assert result != null;
                Log.d("RESULT", new Gson().toJson(result));
            }
        });
    }
}