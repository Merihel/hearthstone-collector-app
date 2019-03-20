package com.example.lpiem.hearthstonecollectorapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.list_card_item_in_deck.view.*


class CardsListInDeckAdapter(var items: MutableList<Card>, val context: Context, val btnlistener: BtnClickListener) : RecyclerView.Adapter<ViewHolderCardsInDeck>() {

    companion object {
        lateinit var fragContext: Context
        var mClickListener: BtnClickListener? = null
    }

    interface Listener {
        fun onItemClicked(item: Card)
    }

    // Gets the number of cards in the list
    override fun getItemCount(): Int {
//        println("items size : "+ (items?.size ?: 0))
//        if (items == null) {
//            return 0
//        } else {
            return items.size

    }

    fun setData(items: MutableList<Card>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        println("remove at")
        items.removeAt(position)
        notifyItemRemoved(position)
        // TODO: remove dans l'API
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCardsInDeck {

        fragContext = context
        return ViewHolderCardsInDeck(LayoutInflater.from(context).inflate(R.layout.list_card_item_in_deck, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderCardsInDeck, position: Int) {
        holder.bind(items.get(position).name)
        mClickListener = btnlistener

        holder.btnRemove.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (mClickListener != null)
                    mClickListener?.onBtnClick(position, holder)
            }
        });

        holder.tvCardName.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (mClickListener != null)
                    mClickListener?.onCardClick(position)
            }
        });


    }

    open interface BtnClickListener {
        fun onBtnClick(position: Int, viewHolder: RecyclerView.ViewHolder)
        fun onCardClick(position: Int)
    }

}

class ViewHolderCardsInDeck (view: View) : RecyclerView.ViewHolder(view) {
    val tvCardName = view.txtCardName
    val btnRemove = view.btnRemove

    fun bind(name: String) {
        tvCardName.text = name
    }


}