package com.example.lpiem.hearthstonecollectorapp.Manager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lpiem.hearthstonecollectorapp.Interface.*
import com.example.lpiem.hearthstonecollectorapp.Models.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class APIManager {
    internal var message: String? = null

    // CARDS

    fun getCardById(id: Int) : MutableLiveData<List<Card>> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCard(id)


        val liveData = MutableLiveData<List<Card>>()

        call.enqueue(object : Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                if (response.isSuccessful) {
                    val card = response.body()
                    Log.d("APIManager", "card text : " + card!!.text!!)
                    val listCard = ArrayList<Card>()
                    listCard.add(card)
                    liveData.postValue(listCard)
                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })

        return liveData
    }

    fun getCardsByUser(userId: Int) : MutableLiveData<List<Card>> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByUser(userId)

        val liveData = MutableLiveData<List<Card>>()

        call.enqueue(object : Callback<List<Card>> {
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if (response.isSuccessful) {
                    val cards = response.body()
                    liveData.postValue(cards)
                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }

    // DECKS
    fun getDecksByUser(userId: Int): MutableLiveData<List<Deck>> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getDecksByUser(userId)
        val liveData = MutableLiveData<List<Deck>>()

        call.enqueue(object : Callback<List<Deck>> {
            override fun onResponse(call: Call<List<Deck>>, response: Response<List<Deck>>) {
                if (response.isSuccessful) {
                    val decks = response.body()
                    liveData.postValue(decks)
                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Deck>>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }

    fun getDeckById(id: Int): MutableLiveData<List<Deck>>  {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getDeck(id)

        val liveData = MutableLiveData<List<Deck>>()

        call.enqueue(object : Callback<Deck> {
            override fun onResponse(call: Call<Deck>, response: Response<Deck>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    val listDeck = ArrayList<Deck>()
                    listDeck.add(deck!!)
                    liveData.postValue((listDeck))
                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Deck>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }

    fun deleteDeckById(deckId: Int): MutableLiveData<JsonObject>{

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.deleteDeckById(deckId)

        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    liveData.postValue(deck)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    fun createDeck(deck: Deck): MutableLiveData<JsonObject> {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        val liveData = MutableLiveData<JsonObject>()

        var json = JsonObject()
        json.addProperty("name", deck.name)
        json.addProperty("description", deck.description)
        json.addProperty("user_id", deck.user?.id)

        var call = hearthstoneInstance!!.createDeck(json)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    liveData.postValue(deck)

                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "error : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    fun updateDeck(deck: Deck): MutableLiveData<JsonObject> {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.updateDeck(deck)
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    liveData.postValue(deck)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "error : " + response.errorBody())

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    // USERS SYNC

    fun syncUserStep1(mail: JsonObject): MutableLiveData<JsonObject>  {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.syncUserStep1(mail)
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    Log.d("APIManager", "sync1: ResponseBody ---> " + response.body())
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "Unsuccessful response")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    fun syncUserStep2(arg: String, json: JsonObject): MutableLiveData<JsonObject> {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.syncUserStep2(arg, json)
        System.out.println(json.toString())
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    Log.d("APIManager", "sync2: ResponseBody ---> " + response.body())
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "Unsuccessful response")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }


    // USERS

    fun getUserByMail(mail: String): MutableLiveData<List<User>> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getUserByMail(mail)
        val liveData = MutableLiveData<List<User>>()

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("APIManager", "user by mail: " + user!!.pseudo!!)
                    var listData = ArrayList<User>()
                    listData.add(user)
                    liveData.postValue(listData)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "getUserByMail-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    fun createUser(user: User): MutableLiveData<JsonObject> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.createUser(user)
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val json = response.body()
                    Log.d("APIManager", "successfully added user with msg: " + json!!.get("devMessage").asString)
                    liveData.postValue(json)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "createUser-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    // USER LOGIN
    fun checkLogin(json: JsonObject): MutableLiveData<User> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.checkLogin(json)
        val liveData = MutableLiveData<User>()

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user?.id != 0 && user?.mail != null) {
                        Log.d("APIManager", "User Logged In: " + user.toString())
                        liveData.postValue(user)
                    } else {
                        liveData.postValue(null)
                        Log.e("APIManager", "Login Error: Une erreur interne est survenue")
                   }

                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    //FRIENDSHIP
    fun getFriendshipsByUser(userId: Int): MutableLiveData<List<Friendship>> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getFriendshipsByUser(userId)
        val liveData = MutableLiveData<List<Friendship>>()

        call.enqueue(object : Callback<List<Friendship>> {
            override fun onResponse(call: Call<List<Friendship>>, response: Response<List<Friendship>>) {
                if (response.isSuccessful) {
                    val friendships = response.body()
                    if (friendships?.size != 0) Log.d("APIManager", "Friendship by user: " + friendships!![0].user1.pseudo + " => " + friendships!![0].user2.pseudo)
                        liveData.postValue(friendships)
                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "getFriendshipByUser-error: " + response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<List<Friendship>>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }

    fun getPendingFriendshipsByUser(userId: Int): MutableLiveData<List<Friendship>> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getPendingFriendshipsByUser(userId)
        val liveData = MutableLiveData<List<Friendship>>()

        call.enqueue(object : Callback<List<Friendship>> {
            override fun onResponse(call: Call<List<Friendship>>, response: Response<List<Friendship>>) {
                if (response.isSuccessful) {
                    val friendships = response.body()
                    Log.d("APIManager", "Pending Friendships by user: " + friendships!![0].user1.pseudo + " => " + friendships!![0].user2.pseudo)
                    liveData.postValue(friendships)
                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "getPendingFriendshipByUser-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<List<Friendship>>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }

    fun deleteFriendship(friendshipId: Int): MutableLiveData<JsonObject> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.deleteFriendship(friendshipId)
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val json = response.body()
                    Log.d("APIManager", "successfully deleted Friendship with msg: " + json!!.get("devMessage").asString)
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "deleteFriendship-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    fun addFriendship(friendship: JsonObject): MutableLiveData<JsonObject> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.addFriendship(friendship)
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val json = response.body()
                    Log.d("APIManager", "successfully added Friendship with msg: " + json!!.get("devMessage").asString)
                    liveData.postValue(json)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "addFriendship-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

    fun acceptFriendship(friendshipId: Int): MutableLiveData<JsonObject> {

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.acceptFriendship(friendshipId)
        val liveData = MutableLiveData<JsonObject>()

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val json = response.body()
                    Log.d("APIManager", "successfully accepted Friendship with msg: " + json!!.get("devMessage").asString)
                    liveData.postValue(json)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "acceptFriendship-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }


    // TRADES //

    fun selectTradeByUser(userId: Int): MutableLiveData<List<Trade>>{

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.selectTradeByUser(userId)
        val liveData = MutableLiveData<List<Trade>>()

        call.enqueue(object : Callback<List<Trade>> {
            override fun onResponse(call: Call<List<Trade>>, response: Response<List<Trade>>) {
                if (response.isSuccessful) {
                    val trades = response.body()
                    liveData.postValue(trades)
                    Log.d("APIManager", "test isAskedOk: " + trades!![0].isAskedOk)

                } else {
                    liveData.postValue(emptyList())
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Trade>>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }

    fun updateStatus(tradeId: Int, tradeStatus: String) : MutableLiveData<JsonObject> {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        val liveData = MutableLiveData<JsonObject>()

        var json = JsonObject()
        json.addProperty("id", tradeId)
        json.addProperty("status", tradeStatus)
        var call = hearthstoneInstance!!.updateStatus(json)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val trade = response.body()
                    liveData.postValue(trade)
                } else {
                    liveData.postValue(null)
                    Log.d("APIManager", "error : " + response.errorBody())

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(null)
            }
        })
        return liveData
    }

}