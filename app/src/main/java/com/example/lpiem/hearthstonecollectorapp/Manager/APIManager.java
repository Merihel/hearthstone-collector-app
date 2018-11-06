package com.example.lpiem.hearthstonecollectorapp.Manager;

import android.util.Log;

import com.example.lpiem.hearthstonecollectorapp.Models.Card;
import com.example.lpiem.hearthstonecollectorapp.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIManager {

    // CARDS //
    public void getCardByID(int id) {
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<Card> call = hearthstoneInstance.getCard(id);


        call.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.isSuccessful()) {
                    Card card = response.body();
                    Log.d("[APIManager]getCardByID", "card text : " + card.getText());

                } else {
                    Log.d("[APIManager]getCardByID", "error on response : " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Card>call, Throwable t) {
                System.out.println("[APIManager]getCardByID Erreur callback ! "+ t);
            }});
    }

    public void getCardBySet(String set){
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<Card> call = hearthstoneInstance.getCardBySet(set);
    }

    public void getCardByClass(String classCard){
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<Card> call = hearthstoneInstance.getCardByClass(classCard);
    }

    public void getCardByRace(String race){
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<Card> call = hearthstoneInstance.getCardByRace(race);
    }

    public void getCardByFaction(String faction){
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<Card> call = hearthstoneInstance.getCardByFaction(faction);
    }

    public void createCard(Card card) {
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<Card> call = hearthstoneInstance.createCard(card);
    }

    // USERS //
    public void getUserByID(int id) {
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<User> call = hearthstoneInstance.getUser(id);
    }

    public void createUser(User user) {
        APIInterface hearthstoneInstance = APISingleton.getInstance();
        Call<User> call = hearthstoneInstance.createUser(user);
    }



}
