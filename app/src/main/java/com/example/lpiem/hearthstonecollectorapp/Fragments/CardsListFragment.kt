package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lpiem.hearthstonecollectorapp.Adapter.CardsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.User

import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.fragment_cards_list.*
import androidx.appcompat.app.AppCompatActivity



private var rootView: View? = null
private var lManager: androidx.recyclerview.widget.GridLayoutManager? = null

class CardsListFragment : InterfaceCallBackCard, InterfaceCallBackUser, androidx.fragment.app.Fragment() {


    companion object {
        fun newInstance(): CardsListFragment {
            return CardsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cards_list, container, false)

        (activity as AppCompatActivity).supportActionBar!!.setTitle("Mes cartes")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = APIManager(this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getCardsByUser(1)
    }

    override fun onWorkCardDone(result: List<Card>) {

    }

    override fun onWorkCardsDone(result: List<Card>) {
        System.out.println("My user cards" + result.toString())

        rv_cards_list.adapter = CardsListAdapter(result, getActivity()!!.applicationContext)
        rv_cards_list.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        //Need to push the cards to the RecyclerView

    }

    override fun onWorkUserDone(result: List<User>) {

    }

}
