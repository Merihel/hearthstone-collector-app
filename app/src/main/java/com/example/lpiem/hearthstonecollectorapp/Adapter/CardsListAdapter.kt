package com.example.lpiem.hearthstonecollectorapp.Adapter;

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.cards_list_item.view.*

class CardsListAdapter(val items: List<Card>, val context: Context) : RecyclerView.Adapter<ViewHolderCards>() {

    companion object {
        lateinit var fragContext: Context
    }


    // Gets the number of cards in the list
    override fun getItemCount(): Int {
        return items.size
    }

    //Inflates the item view - p0=parent, p1=viewType
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderCards {

        fragContext = context
        return ViewHolderCards(LayoutInflater.from(context).inflate(R.layout.cards_list_item, p0, false))
    }


    //Binds each card in the List to some views - p0=viewHolder, p1=position
    override fun onBindViewHolder(p0: ViewHolderCards, p1: Int) {
        p0?.updateWithUrl(items[p1]?.imgGold)

    }
}

class ViewHolderCards (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each card to
    //val tvCardName = view.cardsList_card_name
    val imageView = view.cardsList_card_thumbnail

    fun updateWithUrl(url: String?) {
            Glide.with(CardsListAdapter.fragContext).load(url).into(imageView)
    }
}