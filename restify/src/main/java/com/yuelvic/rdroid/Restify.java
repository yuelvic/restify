package com.yuelvic.rdroid;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by yuelvic on 8/17/16.
 */
public class Restify {

    private static Restify restify;
    private Context context;
    private String baseUrl;
    private HashMap<String, String> baseUrls;

    /**
     * Builder Pattern for initialization
     */
    public static class Builder {
        private Context context;
        private String baseUrl;
        private HashMap<String, String> baseUrls;

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
         * Sets multiple baseUrls
         * @param baseUrls API base urls
         * @return Builder instance
         */
        public Builder setBaseUrls(HashMap<String, String> baseUrls) {
            this.baseUrls = baseUrls;
            return this;
        }

        /**
         * Adds a url to map
         * @param name Url Key
         * @param baseUrl Key value
         * @return Builder instance
         */
        public Builder addUrl(String name, String baseUrl) {
            this.baseUrls.put(name, baseUrl);
            return this;
        }

        /**
         * Initialize Restify
         */
        public void create() {
            restify = new Restify(this);
        }

        /**
         * Builder constructor
         */
        public Builder() {
            this.baseUrls = new HashMap<>();
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

    /**
     * Static method to initialize Restify
     * @param endpoint API baseUrl
     */
    public static void initialize(String endpoint) {
        new Builder().setBaseUrl(endpoint)
                .create();
    }

    /**
     * Static metod to initialize Restify
     * @param baseUrls API baseUrls
     */
    public static void initialize(HashMap<String, String> baseUrls) {
        new Builder().setBaseUrls(baseUrls)
                .create();
    }

    /**
     * Private constructor for Restify
     * @param builder Builder instance for Restify
     */
    private Restify(Builder builder) {
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.baseUrls = builder.baseUrls;

        if (this.baseUrl == null && this.baseUrls != null) {
            this.baseUrl = this.baseUrls.entrySet().iterator().next().getValue();
        }
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

    /**
     * Returns the map of API base urls
     * @return API base urls
     */
    public HashMap<String, String> getBaseUrls() {
        try {
            return baseUrls;
        } catch (NullPointerException e) {
            Log.e("Restify error", "No base url provided.", e);
        }
        return null;
    }

    /**
     * Callback interface
     */
    public interface Call {
        void onCompleted();
        void onError(Throwable e);
        void onSuccess(HashMap<String, Object> result);
    }

}
