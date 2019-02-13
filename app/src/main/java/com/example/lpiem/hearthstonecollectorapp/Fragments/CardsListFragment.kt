package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.example.lpiem.hearthstonecollectorapp.Activities.NavigationActivity
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User

import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_cards_list.*
import kotlinx.android.synthetic.main.fragment_decks_list.*
import kotlinx.android.synthetic.main.toolbar.view.*

private var rootView: View? = null
private var llayout: LinearLayout? = null

class CardsListFragment :  InterfaceCallBackDeck, InterfaceCallBackCard, InterfaceCallBackUser, Fragment() {

    companion object {
        fun newInstance(): CardsListFragment {
            System.out.println("new instance cards list")
            return CardsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cards_list, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gestion de la toolbar
        cards_toolbar.tvTitre.text = "Liste de cartes"
        cards_toolbar.ic_add.visibility = View.GONE

        cards_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }

        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getCardsByUser(1)
    }

    override fun onWorkCardDone(result: List<Card>) {

    }

    override fun onWorkCardsDone(result: List<Card>) {
        System.out.println("My user cards" + result.toString())
        llayout = rootView?.findViewById(R.id.cardsListLayout)
        result.forEach {
            var dynamicTextView = TextView(context)
            dynamicTextView.text = it.name
            llayout?.addView(dynamicTextView)
        }
        //Need to push the cards to the RecyclerView
    }

    override fun onWorkUserDone(result: List<User>) {

    }

    override fun onWorkDeckDone(result: MutableList<Deck>) {
        if (result != null) {
            Log.d("onWorkDeckDone", result.get(0).name)
        }
    }

}
