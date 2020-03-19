package com.cops.sofra.data.api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroritClient {

    private static Retrofit retrofit=null;
    private static final String BASE_URL="http://ipda3-tech.com/sofra-v2/api/v2/";
    public synchronized static ApiService getClient(){
        if (retrofit==null) {
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
