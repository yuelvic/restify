package com.yuelvic.rdroid;

import android.content.Context;
import android.util.Log;

import com.yuelvic.rdroid.util.RestResult;

import java.util.HashMap;

/**
 * Created by yuelvic on 8/17/16.
 */
public class Restify {

    private static Restify restify;
    private Context context;
    private String baseUrl;

    /**
     * Builder Pattern for initialization
     */
    public static class Builder {
        private Context context;
        private String baseUrl;

        /**
         * Sets the application context
         * @return Builder instance
         */
        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        /**
         * Sets the API baseUrl
         * @param baseUrl API base url
         * @return Builder instance
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Initialize Restify
         */
        public void create() {
            restify = new Restify(this);
        }
    }

    /**
     * Static method to initialize Restify
     * @param context Application's context
     * @param endpoint API baseUrl
     */
    public static void initialize(Context context, String endpoint) {
        new Builder().setContext(context)
                .setBaseUrl(endpoint)
                .create();
    }

    public static void initialize(String endpoint) {
        new Builder().setBaseUrl(endpoint)
                .create();
    }

    /**
     * Private constructor for Restify
     * @param builder Builder instance for Restify
     */
    private Restify(Builder builder) {
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
    }

    /**
     * Returns an instance of Restify
     * @return Restify instance
     */
    public static Restify getInstance() {
        try {
            return restify;
        } catch (NullPointerException e) {
            Log.e("Null instance", "You must initialize Restify first.", e);
        }
        return null;
    }

    /**
     * Returns the API base url
     * @return API base url
     */
    public String getBaseUrl() {
        try {
            return baseUrl;
        } catch (NullPointerException e) {
            Log.e("Restify error", "No base url provided.", e);
        }
        return null;
    }

    public interface Call {
        void onCompleted();
        void onError(Throwable e);
        void onSuccess(HashMap<String, Object> result);
    }

}
