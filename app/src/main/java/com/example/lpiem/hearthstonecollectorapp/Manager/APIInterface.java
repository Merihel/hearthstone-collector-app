package com.example.lpiem.hearthstonecollectorapp.Manager;

import com.example.lpiem.hearthstonecollectorapp.Models.Card;
import com.example.lpiem.hearthstonecollectorapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    // NOTE SUR LES CALLS
    // Quand on fait un GET, l'API Symfony va renvoyer un Array, même s'il n'y a qu'un seul résultat. On passe donc des List d'objets au lieu des objets eux-mêmes.

    // CARDS //
    @GET("card/select/{id}")
    Call<Card> getCard( @Path("id") int id);

    @GET("card/select/{set}")
    Call<List<Card>> getCardBySet( @Path("set") String set);

    @GET("card/select/{class}")
    Call<List<Card>> getCardByClass( @Path("class") String classCard);

    @GET("card/select/{race}")
    Call<List<Card>> getCardByRace( @Path("race") String race);

    @GET("card/select/{faction}")
    Call<List<Card>> getCardByFaction( @Path("faction") String faction);

    @POST("card/new")
    Call<Card> createCard(@Body Card card);



    // USERS //
    @GET("/user/select/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("/user/new")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<User> createUser(@Body User user);

    @POST("/user/update")
    Call<User> updateUser(@Body User user);

}
