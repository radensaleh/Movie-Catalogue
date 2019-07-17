package com.example.rdsaleh.searchmovieuiux.retroserver;
import com.example.rdsaleh.searchmovieuiux.BuildConfig;
import com.example.rdsaleh.searchmovieuiux.api.BaseAPI;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static Retrofit mInstance;
    private retrofit2.Retrofit retrofit;

    private Retrofit(){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized Retrofit getInstance(){
        if(mInstance == null){
            mInstance = new Retrofit();
        }
        return mInstance;
    }

    public BaseAPI baseAPI(){
        return retrofit.create(BaseAPI.class);
    }


}
