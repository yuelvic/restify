package com.yuelvic.rdroid.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuelvic on 8/17/16.
 */
public class ApiService {

    public static <T> T createApiService(final Class<T> clazz, final String endpoint) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}
