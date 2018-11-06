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
    @GET("card/select/{id}")
    Call<List<Card>> getCard( @Path("id") int id);

    @GET("card/select/{set}")
    Call<List<Card>> getCardBySet( @Path("set") String set);

    @GET("card/select/{class}")
    Call<List<Card>> getCardByClass( @Path("class") String classCard);

    @GET("card/select/{race}")
    Call<List<Card>> getCardByRace( @Path("race") String race);

    @GET("card/select/{faction}")
    Call<List<Card>> getCardByFaction( @Path("faction") String faction);

    @POST("card/new")
    Call<List<Card>> createCard(@Body Card card);



    // USERS //
    @GET("/user/select/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("user/new")
    Call<User> createUser(@Body User user);

    @GET("/user/update")
    Call<User> getUserWithCards(@Body User user);

}
