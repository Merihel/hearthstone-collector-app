package com.example.lpiem.hearthstonecollectorapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.fragment_decks_list.view.*

class DecksListAdapter (val items: List<Deck>, val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    companion object {
        lateinit var fragContext: Context
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //Inflates the item view - p0=parent, p1=viewType
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        fragContext = context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_deck_item, p0, false))
    }


    //Binds each card in the List to some views - p0=viewHolder, p1=position
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //p0?.updateWithUrl(items.get(p1).name)

    }
}

class ViewHolder (view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    //val tvCardName = view.cardsList_card_name
}