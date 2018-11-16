package com.example.lpiem.hearthstonecollectorapp.Manager

import android.util.Log
import com.example.lpiem.hearthstonecollectorapp.Interface.APIInterface
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIManager (internal var interfaceCallBackUser: InterfaceCallBackUser, internal var interfaceCallBackCard: InterfaceCallBackCard) {
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
                    interfaceCallBackCard.onWorkCardDone(listCard)
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                t.printStackTrace()
            }
        })


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
                    Log.d("APIManager", "error : " + response.errorBody()!!)
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
                    interfaceCallBackUser.onWorkUserDone(listData)
                } else {
                    Log.d("APIManager", "error : " + response.errorBody()!!)
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

//    @Synchronized
//    private fun fetchData(response: Response<Card>) {
//
//        for (i in 0 until response.body()!!.size) {
//            listName.add(response.body()!![i].card!!.name!!)
//        }
//        interfaceCallBackController.onWorkDone(true)
//    }




}