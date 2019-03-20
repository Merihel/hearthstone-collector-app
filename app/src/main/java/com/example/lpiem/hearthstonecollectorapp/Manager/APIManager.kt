package com.example.lpiem.hearthstonecollectorapp.Manager

import android.util.Log
import com.example.lpiem.hearthstonecollectorapp.Interface.*
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import java.security.AccessController.getContext


class APIManager (internal var interfaceCallBackUser: InterfaceCallBackUser? = null,
                  internal var interfaceCallBackCard: InterfaceCallBackCard? = null,
                  internal var interfaceCallBackSync: InterfaceCallBackSync? = null,
                  internal var interfaceCallBackLogin: InterfaceCallBackLogin? = null,
                  internal var interfaceCallBackDeck: InterfaceCallBackDeck? = null) {
    internal var message: String? = null
    internal var nextPage = 1
    internal var nbPages = 100

    // CARDS

    fun getCardById(id: Int) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCard(id)


        call.enqueue(object : Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                if (response.isSuccessful) {
                    val card = response.body()
                    Log.d("APIManager", "card text : " + card!!.text!!)
                    val listCard = ArrayList<Card>()
                    listCard.add(card)
                    interfaceCallBackCard?.onWorkCardDone(listCard)
                } else {
                    Log.d("APIManager", "getCardById-error: " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getCardById2(id: Int) : MutableLiveData<List<Card>> {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

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

                    //interfaceCallBackCard.onWorkCardDone(listCard)
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

    fun getCardsBySet(set: String) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsBySet(set)


        call.enqueue(object : Callback<List<Card>> {
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if (response.isSuccessful) {
                    // TODO : gérer la liste
                    val card = response.body()
                    //fetchData(response)
                    //Log.d("APIManager", "card text : " + card!!.text!!)

                } else {
                    Log.d("APIManager", "getCardsBySet-error: " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun getCardsByRace(race: String) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByRace(race)


        call.enqueue(object : Callback<List<Card>> {
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if (response.isSuccessful) {
                    val card = response.body()
                    val listCard = ArrayList<Card>()
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun getCardsByFaction(faction: String) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByFaction(faction)


        call.enqueue(object : Callback<List<Card>> {
            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if (response.isSuccessful) {
                    // TODO : Gérer la liste !
                    val card = response.body()
                    //fetchData(response)
                    //Log.d("APIManager", "card text : " + card!!.text!!)

                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }
        })
    }

    fun getCardsByUser(userId: Int) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getCardsByUser(userId)


        call.enqueue(object : Callback<List<Card>> {
            override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                if (response.isSuccessful) {
                    val cards = response.body()

                    interfaceCallBackCard?.onWorkCardsDone(cards!!)

                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun createCard(card: Card) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.createCard(card)


        call.enqueue(object : Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                if (response.isSuccessful) {
                    val card = response.body()
                    //fetchData(response)
                    Log.d("APIManager", "card text : " + card!!.text!!)

                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    // DECKS
    fun getDecksByUser(userId: Int) {
        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getDecksByUser(userId)

        call.enqueue(object : Callback<List<Deck>> {
            override fun onResponse(call: Call<List<Deck>>, response: Response<List<Deck>>) {
                if (response.isSuccessful) {
                    val decks = response.body()
                    interfaceCallBackDeck?.onWorkDecksDone(decks!!)
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<Deck>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getDeckById(id: Int) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getDeck(id)

        call.enqueue(object : Callback<Deck> {
            override fun onResponse(call: Call<Deck>, response: Response<Deck>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    val listDeck = ArrayList<Deck>()
                    listDeck.add(deck!!)
                    interfaceCallBackDeck?.onWorkDeckDone(listDeck)
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Deck>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun deleteDeckById(deckId: Int){
        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.deleteDeckById(deckId)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    interfaceCallBackDeck?.onWorkDeleteDeckDone(deck!!)
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun createDeck(deck: Deck) {
        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!
        var hearthstoneInstance = APISingleton.hearthstoneInstance

        var json = JsonObject()
        json.addProperty("name", deck.name)
        json.addProperty("description", deck.description)
        json.addProperty("user_id", deck.user?.id)

        var call = hearthstoneInstance!!.createDeck(json)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    interfaceCallBackDeck?.onWorkDeckAddedDone(deck!!)

                } else {
                    Log.d("APIManager", "error : " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun updateDeck(deck: Deck){
        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.updateDeck(deck)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val deck = response.body()
                    interfaceCallBackDeck?.onWorkDeckUpdatedDone(deck!!)

                } else {
                    Log.d("APIManager", "error : " + response.errorBody())

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    // USERS SYNC

    fun syncUserStep1(mail: JsonObject) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.syncUserStep1(mail)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    Log.d("APIManager", "sync1: ResponseBody ---> " + response.body())
                    interfaceCallBackSync?.onWorkSyncDone(response.body()!!)
                } else {
                    Log.d("APIManager", "Unsuccessful response")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun syncUserStep2(arg: String, json: JsonObject) {
        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.syncUserStep2(arg, json)
        System.out.println(json.toString())

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    Log.d("APIManager", "sync2: ResponseBody ---> " + response.body())
                    interfaceCallBackSync?.onWorkSyncDone2(response.body()!!)
                } else {
                    Log.d("APIManager", "Unsuccessful response")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    // USERS

    fun getUserById(id: Int) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getUser(id)


        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("APIManager", "card text : " + user!!.firstName!!)
                    var listData = ArrayList<User>()
                    listData.add(user)
                    interfaceCallBackUser?.onWorkUserDone(listData)
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getUserByMail(mail: String) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.getUserByMail(mail)


        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("APIManager", "user by mail: " + user!!.pseudo!!)
                    var listData = ArrayList<User>()
                    listData.add(user)
                    interfaceCallBackUser?.onWorkUserDone(listData)
                } else {
                    Log.d("APIManager", "getUserByMail-error: " + response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun createUser(user: User) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.createUser(user)


        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    //fetchData(response)
                    Log.d("[APIManager]createUser", "card text : " + user!!.toString()!!)

                } else {
                    Log.d("[APIManager]createUser", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    // USER LOGIN
    fun checkLogin(json: JsonObject) {

        var hearthstoneApi: APIInterface = APISingleton.hearthstoneInstance!!

        var hearthstoneInstance = APISingleton.hearthstoneInstance
        var call = hearthstoneInstance!!.checkLogin(json)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user?.id != 0 && user?.mail != null) {
                        Log.d("APIManager", "User Logged In: " + user?.toString())
                        interfaceCallBackLogin?.onWorkLoginDone(user)
                    } else {
                        Log.e("APIManager", "Login Error: Une erreur interne est survenue")
                        interfaceCallBackLogin?.onWorkLoginError("Connexion: Utilisateur ou mot de passe incorrect")
                    }

                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

//    @Synchronized
//    private fun fetchData(response: Response<Card>) {
//
//        for (i in 0 until response.body()!!.size) {
//            listName.add(response.body()!![i].card!!.name!!)
//        }
//        interfaceCallBackController.onWorkDone(true)
//    }




}