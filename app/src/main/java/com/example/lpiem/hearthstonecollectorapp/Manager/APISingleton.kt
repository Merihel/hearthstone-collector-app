package com.example.lpiem.hearthstonecollectorapp.Manager

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APISingleton {

    private val URL_API = "http://10.0.2.2:8000"
    private var hearthstoneInstance: APIInterface? = null

    fun getInstance(): APIInterface? {
        if (hearthstoneInstance == null)
            createApiBuilder()
        
        return hearthstoneInstance
    }


    private fun createApiBuilder() { // dans l'init ?
        val httpClient = OkHttpClient.Builder()

        val gson = GsonBuilder()
                .create()

        val builder = Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))

        val retrofit = builder
                .client(httpClient.build())
                .build()

        hearthstoneInstance = retrofit.create(APIInterface::class.java)
    }


}
