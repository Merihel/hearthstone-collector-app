package com.example.lpiem.hearthstonecollectorapp.Manager

import android.util.Log
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIManager {

    // CARDS //
    fun getCardById(id: Int) {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCard(id)

        call.enqueue(object: Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                if (response.isSuccessful) {
                    val card = response.body()
                    Log.d("[APIManager]getCardByID", "Card text : " + card!!.text)
                } else {
                    Log.d("[APIManager]getCardByID", "Error: " + response.errorBody())
                }
            }
            override fun onFailure(call: Call<Card>, t: Throwable) {
                Log.e("APIManager", t.message)
            }
        })
    }

    fun getCardsBySet(set: String) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsBySet(set)
    }

    fun getCardsByClass(classCard: String) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByClass(classCard)
    }

    fun getCardsByRace(race: String) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByRace(race)
    }

    fun getCardsByFaction(faction: String) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByFaction(faction)
    }

    fun createCard(card: Card) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.createCard(card)
    }

    // USERS //
    fun getUserById(id: Int) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getUser(id)

        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    var user = response.body()
                    Log.d("[APIManager]getUserById", user.toString())
                } else {
                    Log.e("[APIManager]getUserById", "Error: " + response.errorBody())
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("[APIManager]getUserById", "Erreur callback: " + t.message)
            }
        })
    }

    fun createUser(user: User) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.createUser(user)

        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    var user = response.body()
                    Log.d("[APIManager]createUser", user.toString())
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("[APIManager]creatUser", "Erreur callback "+t.message)
            }
        })
    }
}