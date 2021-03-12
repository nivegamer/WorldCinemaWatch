package com.example.worldcinema;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network mInstance;
    private static Retrofit retrofit;
    private static final String BASE_URL="http://cinema.areas.su/";



    private Network() {
        Gson gson = new GsonBuilder().setLenient().create();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Network getInstance() {
        if (mInstance == null) {
            mInstance = new Network();
        }
        return mInstance;
    }

    public static Rest getRest(){
        return retrofit.create(Rest.class);
    }


}
