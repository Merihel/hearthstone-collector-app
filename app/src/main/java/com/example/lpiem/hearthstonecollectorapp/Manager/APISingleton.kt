package com.example.lpiem.hearthstonecollectorapp.Manager

import com.example.lpiem.hearthstonecollectorapp.Interface.APIInterface
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APISingleton {
    private val URL_API = "http://172.31.247.232:8000/" //"http://nilsw-iem.ddns.net"
    var hearthstoneInstance: APIInterface? = null
    get() {
        if (field == null)
            createApiBuilder()

        return field
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
