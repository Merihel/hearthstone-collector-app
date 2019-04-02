package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lpiem.hearthstonecollectorapp.Activities.NavigationActivity
import com.example.lpiem.hearthstonecollectorapp.Adapter.TradeListAdapter
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackTrade
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Trade
import com.example.lpiem.hearthstonecollectorapp.Models.User

import com.example.lpiem.hearthstonecollectorapp.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_trade_list.*
import kotlinx.android.synthetic.main.toolbar.view.*

private var rootView: View? = null

class TradeListFragment : Fragment(), InterfaceCallBackTrade {
    private var trades = mutableListOf<Trade>()
    private lateinit var adapter : TradeListAdapter

    companion object {
        fun newInstance(): TradeListFragment {
            System.out.println("new instance trade list")
            return TradeListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_trade_list, container, false)
        return rootView
    }

    override fun onResume() {
        println("on resume trade list")
        super.onResume()
        val controller = APIManager()

        // Valeurs de test
        val userAsked = User(null, null, null, null, "Jean", "Truc", null, null, null, null, null, null)
        val cardAsker = Card(0, "0", "invocateur", 10, 5, true, 1, 2, "blabla", "guerrier", "test", "test", "test", "test", "test", "test", "test", "test", "test")
        trades.add(0, Trade(null, null, userAsked, null, cardAsker, null, null, null))
        trades.add(1, Trade(null, null, userAsked, null, cardAsker, null, null, null))

       // controller.getDecksByUser(HsUserManager.loggedUser.id!!, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = APIManager()

        // Gestion de la toolbar
        trades_toolbar.tvTitre.text = "Mes échanges"
        trades_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }
        trades_toolbar.ic_add.visibility = View.INVISIBLE

        // Adapter et layout manager
        val listener = object : TradeListAdapter.Listener {
            override fun onItemClicked(item: Trade) {
                //val intent = Intent(activity, TradeDetailActivity::class.java)
                //intent.putExtra("tradeId", item.id)
                //activity!!.startActivity(intent)
            }
        }
        adapter = TradeListAdapter(trades, activity!!.applicationContext, listener)
        recycler_view_trades.layoutManager = LinearLayoutManager(context)
        recycler_view_trades.adapter = adapter

    }

    override fun onWorkTradesDone(result: JsonObject) {   }
    override fun onWorkTradeAdded(result: JsonObject) {   }


}
