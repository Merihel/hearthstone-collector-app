package com.example.lpiem.hearthstonecollectorapp.Manager;

import com.example.lpiem.hearthstonecollectorapp.HearthstoneService;
import com.example.lpiem.hearthstonecollectorapp.Models.Card;
import com.example.lpiem.hearthstonecollectorapp.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private String URL_API = "http://10.0.2.2:8000"; //http://127.0.0.1:8000/
    private HearthstoneService client;

    public APIManager(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .create();



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        client = retrofit.create(HearthstoneService.class);
    }


    // CARDS //
    public void getCardByID(int id) {
        Call<Card> card = client.getCard(id);

        card.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                // The network call was a success and we got a response
                // TODO: display the card
                System.out.println("[APIManager]getCardByID response : "+ response.body().getText());
            }

            @Override
            public void onFailure(Call<Card>call, Throwable t) {
                System.out.println("[APIManager]getCardByID Erreur callback ! "+ t);

            }});
    }

    public void newCard(Card card) {
        Call<Card> newCard = client.createCard(card);
    }

    // USERS //
    public void getUserByID(int id) {
        Call<User> user = client.getUser(id);
    }



}
