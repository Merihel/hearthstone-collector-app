package com.example.lpiem.hearthstonecollectorapp;

import com.example.lpiem.hearthstonecollectorapp.Models.Card;
import com.example.lpiem.hearthstonecollectorapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HearthstoneService {
    // CARDS //

    @GET("card/{id}")
    Call<Card> getCard( @Path("id") int id);

    @POST("/new-card")
    Call<Card> createCard(@Body Card card);



    // USERS //
    @GET("/user/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("/new-user")
    Call<User> createUser(@Body User user);

}
