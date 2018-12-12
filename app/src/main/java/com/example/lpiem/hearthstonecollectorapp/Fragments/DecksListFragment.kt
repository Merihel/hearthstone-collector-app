package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Adapter.DecksListAdapter
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User

import com.example.lpiem.hearthstonecollectorapp.R
import com.example.lpiem.hearthstonecollectorapp.Manager.FragmentToolbar
import kotlinx.android.synthetic.main.fragment_decks_list.*


private var rootView: View? = null
private var llayout: LinearLayout? = null

class DecksListFragment : BaseFragment(), InterfaceCallBackDeck, InterfaceCallBackUser, InterfaceCallBackCard {
    private var decks = ArrayList<Deck>()
    private var rootView: View? = null
    private var lManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null

    companion object {
        fun newInstance(): CardsListFragment {
            System.out.println("new instance decks list")
            return CardsListFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cards_list, container, false)
        getActivity()?.setTitle("Mes decks");
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getDecksByUser(1)
    }

    override fun onWorkDeckDone(result: List<Deck>) {
        System.out.println("My user decks" + result.toString())
        recycler_view_decks.adapter = DecksListAdapter(result, getActivity()!!.applicationContext)
        //recycler_view_decks.layoutManager = LinearLayoutManager(context, 3)
    }

    override fun onWorkUserDone(result: List<User>) {
    }

    override fun onWorkCardDone(result: List<Card>) {
    }

    override fun onWorkCardsDone(result: List<Card>) {
    }

    override protected fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
                .withId(R.id.toolbar)
                .withTitle(R.string.nav_drawer_decks)
                .withMenu(R.menu.fragment_deckslist)
                .build()
    }

}
