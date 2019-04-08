package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lpiem.hearthstonecollectorapp.Activities.NavigationActivity
import com.example.lpiem.hearthstonecollectorapp.Adapter.TradeListAdapter
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackTrade
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Trade

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
            return TradeListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_trade_list, container, false)
        return rootView
    }

    override fun onResume() {
       super.onResume()
       val controller = APIManager()
       controller.selectTradeByUser(HsUserManager.loggedUser.id!!, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = APIManager()

        // Gestion de la toolbar
        trades_toolbar.tvTitre.text = getString(R.string.trade_navbar)
        trades_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }
        trades_toolbar.ic_add.visibility = View.INVISIBLE

        // Adapter et layout manager
        val listener = object : TradeListAdapter.Listener {
            override fun onDeleteClicked(item: Trade) {
                controller.updateStatus(item.id!!, "OUT", this@TradeListFragment)
            }
            override fun onAcceptClicked(item: Trade) {
                controller.updateStatus(item.id!!, "OK", this@TradeListFragment)
            }
        }
        adapter = TradeListAdapter(trades, activity!!.applicationContext, listener)
        recycler_view_trades.layoutManager = LinearLayoutManager(context)
        recycler_view_trades.adapter = adapter

    }

    override fun onWorkTradesDone(result: List<Trade>) {
        System.out.println("My trades" + result.toString())
        trades.clear()

        for(trade in result){
            // On ajoute uniquement dans la liste les échanges qui sont en attente
            if(trade.status == "PENDING"){
                trades.add(trade)
            }
        }

        adapter.setData(trades)
    }

    override fun onWorkTradeUpdated(result: JsonObject) {
        Log.d("mlk", result.get("message").asString)
        Toast.makeText(context, result.get("message").asString, Toast.LENGTH_SHORT).show()

        val controller = APIManager()
        controller.selectTradeByUser(HsUserManager.loggedUser.id!!, this)
        adapter.notifyDataSetChanged()
    }

    override fun onWorkTradeAdded(result: JsonObject) {    }


}
