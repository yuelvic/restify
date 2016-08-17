package com.yuelvic.rdroid.util;

import com.google.gson.Gson;
import com.yuelvic.rdroid.Restify;
import com.yuelvic.rdroid.http.ApiService;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuelvic on 8/17/16.
 */
public class RestObject {

    private Restify restify;
    private CRUD service;
    private String endpoint;
    private HashMap<String, Object> body;

    /**
     * Builder pattern for RestObject
     */
    public static class Builder {
        private String endpoint;
        private HashMap<String, Object> body;

        /**
         * Sets the API endpoint
         * @param endpoint API endpoint
         * @return Builder instance
         */
        public Builder setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * Puts a string value to body
         * @param key Body key
         * @param value Key value
         * @return Builder instance
         */
        public Builder putString(String key, String value) {
            body.put(key, value);
            return this;
        }

        /**
         * Puts an integer to body
         * @param key Body key
         * @param value Key value
         * @return Builder instance
         */
        public Builder putInt(String key, int value) {
            body.put(key, value);
            return this;
        }

        /**
         * Puts a double to body
         * @param key Body key
         * @param value Key value
         * @return Builder instance
         */
        public Builder putDouble(String key, double value) {
            body.put(key, value);
            return this;
        }

        /**
         * Puts a boolean to body
         * @param key Body key
         * @param value Key value
         * @return Builder instance
         */
        public Builder putBoolean(String key, boolean value) {
            body.put(key, value);
            return this;
        }

        /**
         * Creates an instance of RestObject
         */
        public RestObject create() {
            return new RestObject(this);
        }

        /**
         * Builder constructor
         */
        public Builder() {
            body = new HashMap<>();
        }
    }

    /**
     * Empty constructor
     */
    public RestObject() {}

    /**
     * Constructor with endpoint
     * @param endpoint API endpoint
     * @param body body
     */
    public RestObject(String endpoint, HashMap<String, Object> body) {
        this.endpoint = endpoint;
        this.body = body;
    }

    /**
     * Returns API endpoint
     * @return API endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets API endpoint
     * @param endpoint API endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Returns API body
     * @return API body
     */
    public HashMap<String, Object> getBody() {
        return body;
    }

    /**
     * Returns API body as JSON
     * @return API body
     */
    public String getJsonBody() {
        return new Gson().toJson(body);
    }

    /**
     * Sets API body
     * @param map API body
     */
    public void setBody(HashMap<String, Object> map) {
        this.body = map;
    }

    /**
     * Creates an object to remote
     * @param call Call interface
     */
    public void create(final Restify.Call call) {
        service.post(endpoint, body).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RestResult>() {
                    @Override
                    public void onCompleted() {
                        call.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        call.onError(e);
                    }

                    @Override
                    public void onNext(RestResult result) {
                        call.onSuccess(result);
                    }
                });
    }

    /**
     * Private constructor for RestObject
     * @param builder Builder instance
     */
    private RestObject(Builder builder) {
        this.endpoint = builder.endpoint;
        this.body = builder.body;
        this.restify = Restify.getInstance();
        this.service = ApiService.createApiService(CRUD.class, restify.getBaseUrl());
    }

    public interface CRUD {
        @POST("{endpoint}")
        Observable<RestResult> post(String endpoint, @Body HashMap<String, Object> body);
    }

}
