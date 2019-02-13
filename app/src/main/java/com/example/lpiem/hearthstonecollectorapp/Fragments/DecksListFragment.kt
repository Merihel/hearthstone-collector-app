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
import kotlinx.android.synthetic.main.fragment_decks_list.*


private var rootView: View? = null
private var lManager: androidx.recyclerview.widget.LinearLayoutManager? = null

class DecksListFragment : Fragment(), InterfaceCallBackDeck, InterfaceCallBackUser, InterfaceCallBackCard {
    private var decks = emptyList<Deck>()
    private var decks2 = mutableListOf<Deck>()
    private lateinit var adapter : DecksListAdapter

    companion object {
        fun newInstance(): CardsListFragment {
            System.out.println("new instance decks list")
            return CardsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_decks_list, container, false)

        getActivity()?.title = "Mes decks";
        println("on create view decks")
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("on view created decks")
        adapter = DecksListAdapter(emptyList(), activity!!.applicationContext)
        recycler_view_decks.layoutManager = LinearLayoutManager(context)
        recycler_view_decks.adapter = adapter

        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getDecksByUser(1)
    }

    override fun onWorkDeckDone(result: List<Deck>) {
        System.out.println("My user decks" + result.toString())
        decks = result

        decks2.clear()
        decks2.addAll(result)

        adapter.setData(result)
    }

    override fun onWorkUserDone(result: List<User>) {
    }

    override fun onWorkCardDone(result: List<Card>) {
    }

    override fun onWorkCardsDone(result: List<Card>) {
    }

}
