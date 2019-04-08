package com.example.lpiem.hearthstonecollectorapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Trade
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.list_trade_item.view.*

class TradeListAdapter (var items: MutableList<Trade>, val context: Context, var listener: Listener) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolderTrade>() {

    companion object {
        lateinit var fragContext: Context
    }

    interface Listener {
        fun onDeleteClicked(item: Trade)
        fun onAcceptClicked(item: Trade)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(items: MutableList<Trade>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        println("remove at")
        items.removeAt(position)
        notifyItemRemoved(position)
        // supprimer dans la base
        // val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        //controller.deleteDeckByUser(1, 1)
    }

    //Inflates the item view - p0=parent, p1=viewType
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderTrade {
        fragContext = context
        return ViewHolderTrade(LayoutInflater.from(context).inflate(R.layout.list_trade_item, p0, false))
    }


    override fun onBindViewHolder(viewHolder: ViewHolderTrade, position: Int) {

       if (items.get(position).userAsker!!.id == HsUserManager.loggedUser.id!!) {
           Log.d("mlk", "suppr check");
           viewHolder.hide()
        }

        viewHolder.bind(items.get(position).cardAsker!!.name, items.get(position).userAsked!!)
        viewHolder.btnDelete.setOnClickListener {
            listener.onDeleteClicked(items[position])
        }
        viewHolder.btnAccept.setOnClickListener {
            listener.onAcceptClicked(items[position])
        }
    }


}

class ViewHolderTrade (view: View) : RecyclerView.ViewHolder(view) {
    val tvCardName = view.tv_card_name
    val tvUserName = view.tv_user_name
    val btnDelete = view.btnDelete
    val btnAccept = view.btnOk

    fun bind(CardName: String, userAsked: User) {
        tvCardName.text = CardName
        tvUserName.text = userAsked.firstName.toString() + " " + userAsked.lastName
    }

    fun hide(){
        btnAccept.visibility = View.INVISIBLE
    }



}