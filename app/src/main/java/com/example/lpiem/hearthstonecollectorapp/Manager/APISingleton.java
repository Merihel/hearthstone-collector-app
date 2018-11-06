package com.example.lpiem.hearthstonecollectorapp.Manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APISingleton {

    private static String URL_API = "http://10.0.2.2:8000";
    private static APIInterface hearthstoneInstance;

    public static APIInterface getInstance(){
        if (hearthstoneInstance == null)
            synchronized (APISingleton.class){
                createApiBuilder();
            }
            return hearthstoneInstance;
    }


    private static void createApiBuilder(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        hearthstoneInstance = retrofit.create(APIInterface.class);
    }
}
