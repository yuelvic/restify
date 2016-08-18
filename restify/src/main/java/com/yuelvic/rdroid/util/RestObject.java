package com.yuelvic.rdroid.util;

import com.google.gson.Gson;
import com.yuelvic.rdroid.Restify;
import com.yuelvic.rdroid.http.ApiService;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
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
    private String baseUrl;
    private String endpoint;
    private boolean urlFormEncoded;
    private HashMap<String, String> baseUrls;
    private HashMap<String, Object> header;
    private HashMap<String, Object> body;
    private HashMap<String, Object> constraint;

    private Observable<HashMap<String, Object>> observable;

    /**
     * Builder pattern for RestObject
     */
    public static class Builder {
        private Restify restify;
        private String baseUrl;
        private String endpoint;
        private boolean urlFormEncoded;
        private HashMap<String, String> baseUrls;
        private HashMap<String, Object> header;
        private HashMap<String, Object> body;
        private HashMap<String, Object> constraint;

        /**
         * Sets the API endpoint based on given urls
         * @param name URL key name
         * @return Builder instance
         */
        public Builder useBaseUrl(String name) {
            this.baseUrl = baseUrls.get(name);
            return this;
        }

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
         * Sets if request is form encoded
         * @param urlFormEncoded Use form encoded or not
         * @return Builder instance
         */
        public Builder setUrlFormEncoded(boolean urlFormEncoded) {
            this.urlFormEncoded = urlFormEncoded;
            return this;
        }

        /**
         * Adds a header
         * @param key Header key
         * @param value Key value
         * @return Builder instance
         */
        public Builder addHeader(String key, Object value) {
            header.put(key, value);
            return this;
        }

        /**
         * Puts a constraint
         * @param key Param key
         * @param value Key value
         * @return Builder instance
         */
        public Builder addConstraint(String key, Object value) {
            constraint.put(key, value);
            return this;
        }

        /**
         * Puts a value to body
         * @param key Body key
         * @param value Key value
         * @return Builder instance
         */
        public Builder addField(String key, Object value) {
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
         * Fetches all request occurrences
         * @param call Callback interface
         * @return RestObject instance
         */
        public RestObject findAll(Restify.Call call) {
            RestObject restObject = new RestObject(this);
            restObject.findAll(call);
            return restObject;
        }

        /**
         * Builder constructor
         */
        public Builder() {
            this.restify = Restify.getInstance();
            this.header = new HashMap<>();
            this.body = new HashMap<>();
            this.constraint = new HashMap<>();

            this.baseUrls = this.restify.getBaseUrls();
        }
    }

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
     * Sets the API endpoint based on given urls
     * @param name URL key name
     */
    public void useBaseUrl(String name) {
        this.baseUrl = baseUrls.get(name);
        this.service = ApiService.createApiService(CRUD.class, this.baseUrl);
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
     * Sets if request is form encoded
     * @param urlFormEncoded Use form encoded or not
     */
    public void setUrlFormEncoded(boolean urlFormEncoded) {
        this.urlFormEncoded = urlFormEncoded;
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
     * Puts a header
     * @param key Header Key
     * @param value Key value
     */
    public void addHeader(String key, Object value) {
        header.put(key, value);
    }

    /**
     * Adds a constraint
     * @param key Constraint key
     * @param value Key value
     */
    public void addConstraint(String key, Object value) {
        constraint.put(key, value);
    }

    /**
     * Puts a body field
     * @param key Field key
     * @param value Key value
     */
    public void addField(String key, Object value) {
        body.put(key, value);
    }

    /**
     * Creates an object to remote
     */
    public void create(Restify.Call call) {
        observable = (urlFormEncoded) ? service.encodedCreate(header, endpoint, body) : service.create(header, endpoint, body);
        execute(call);
    }

    /**
     * Returns all objects
     * @param call Call instance
     */
    public void findAll(Restify.Call call) {
        observable = service.findAll(header, endpoint, constraint);
        execute(call);
    }

    /**
     * Return an object by ID
     * @param id Object ID
     * @param call Call instance
     */
    public void findById(int id, Restify.Call call) {
        observable = service.findById(header, endpoint, id);
        execute(call);
    }

    /**
     * Updates an object
     * @param id Object ID
     * @param call Call instance
     */
    public void update(int id, Restify.Call call) {
        observable = (urlFormEncoded) ? service.encodedUpdate(header, endpoint, id, body) : service.update(header, endpoint, id, body);
        execute(call);
    }

    /**
     * Deletes an object
     * @param id Object ID
     * @param call Call instance
     */
    public void delete(int id, Restify.Call call) {
        observable = service.delete(header, endpoint, id);
        execute(call);
    }

    /**
     * Executes the call
     * @param call Call interface
     */
    private void execute(final Restify.Call call) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HashMap<String, Object>>() {
                    @Override
                    public void onCompleted() {
                        call.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        call.onError(e);
                    }

                    @Override
                    public void onNext(HashMap<String, Object> result) {
                        call.onSuccess(result);
                    }
                });
    }

    /**
     * Empty constructor
     */
    public RestObject() {
        this.restify = Restify.getInstance();
        this.baseUrls = restify.getBaseUrls();
        this.header = new HashMap<>();
        this.body = new HashMap<>();
        this.constraint = new HashMap<>();
    }

    /**
     * Private constructor for RestObject
     * @param builder Builder instance
     */
    private RestObject(Builder builder) {
        this.restify = builder.restify;
        this.baseUrl = builder.baseUrl;
        this.endpoint = builder.endpoint;
        this.baseUrls = builder.baseUrls;
        this.urlFormEncoded = builder.urlFormEncoded;
        this.header = builder.header;
        this.body = builder.body;
        this.constraint = builder.constraint;
        this.service = ApiService.createApiService(CRUD.class, this.baseUrl);
    }

    /**
     * API routes
     */
    public interface CRUD {
        @POST("{endpoint}")
        Observable<HashMap<String, Object>> create(@HeaderMap HashMap<String, Object> header,
                                                   @Path(value = "endpoint", encoded = true) String endpoint,
                                                   @Body HashMap<String, Object> body);
        @FormUrlEncoded
        @POST("{endpoint}")
        Observable<HashMap<String, Object>> encodedCreate(@HeaderMap HashMap<String, Object> header,
                                                   @Path(value = "endpoint", encoded = true) String endpoint,
                                                   @FieldMap HashMap<String, Object> body);
        @GET("{endpoint}")
        Observable<HashMap<String, Object>> findAll(@HeaderMap HashMap<String, Object> header,
                                                    @Path(value = "endpoint", encoded = true) String endpoint,
                                                    @QueryMap HashMap<String, Object> constraint);
        @GET("{endpoint}/{id}")
        Observable<HashMap<String, Object>> findById(@HeaderMap HashMap<String, Object> header,
                                                     @Path(value = "endpoint", encoded = true) String endpoint,
                                                     @Path("id") int id);
        @PUT("{endpoint}/{id}")
        Observable<HashMap<String, Object>> update(@HeaderMap HashMap<String, Object> header,
                                                   @Path(value = "endpoint", encoded = true) String endpoint,
                                                   @Path("id") int id, @Body HashMap<String, Object> body);
        @FormUrlEncoded
        @PUT("{endpoint}/{id}")
        Observable<HashMap<String, Object>> encodedUpdate(@HeaderMap HashMap<String, Object> header,
                                                   @Path(value = "endpoint", encoded = true) String endpoint,
                                                   @Path("id") int id, @FieldMap HashMap<String, Object> body);
        @DELETE("{endpoint}/{id}")
        Observable<HashMap<String, Object>> delete(@HeaderMap HashMap<String, Object> header,
                                                   @Path(value = "endpoint", encoded = true) String endpoint,
                                                   @Path("id") int id);
    }

}
