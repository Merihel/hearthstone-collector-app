package com.example.lpiem.hearthstonecollectorapp.Manager;

import com.example.lpiem.hearthstonecollectorapp.Models.Card;
import com.example.lpiem.hearthstonecollectorapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    // CARDS //

    @GET("card/{id}")
    Call<Card> getCard( @Path("id") int id);

    @GET("card/{set}")
    Call<Card> getCardBySet( @Path("set") String set);

    @GET("card/{class}")
    Call<Card> getCardByClass( @Path("class") String classCard);

    @GET("card/{race}")
    Call<Card> getCardByRace( @Path("race") String race);

    @GET("card/{faction}")
    Call<Card> getCardByFaction( @Path("faction") String faction);

    @POST("/new-card")
    Call<Card> createCard(@Body Card card);



    // USERS //
    @GET("/user/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("/new-user")
    Call<User> createUser(@Body User user);

    @GET("/user/select-with-cards/{id}")
    Call<User> getUserWithCards(@Body User user);

}
