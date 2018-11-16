package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lpiem.hearthstonecollectorapp.Manager.APISingleton
import com.example.lpiem.hearthstonecollectorapp.Models.User

import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.fragment_cards_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardsListFragment : Fragment() {

    fun newInstance(): CardsListFragment {
        return CardsListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*
         * TEST : récupérer le user avec l'id 1
         */

        val hearthstoneInstance = APISingleton.hearthstoneInstance
        val callUser = hearthstoneInstance!!.getUser(1)
        callUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body() //On get l'objet à l'index 0

                    Log.d("[ConnexionActivity]", "User  : " + user!!.cards!![0].id)
                    val cardId = user.cards!![0].id
                    println(cardId)
                    txtTest.text = "Cartes user : $cardId"
                } else {
                    Log.d("[ConnexionActivity]", "error on response : " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("[APIManager]getUserByID Erreur callback ! $t")
            }
        })
    }

}
