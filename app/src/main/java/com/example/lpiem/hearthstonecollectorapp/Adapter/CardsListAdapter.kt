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
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions





class CardsListAdapter(val items: List<Card>, val context: Context, var listener: Listener) : RecyclerView.Adapter<ViewHolderCards>() {

    companion object {
        lateinit var fragContext: Context
    }

    interface Listener {
        fun onItemClicked(item: Card)
    }

    // Gets the number of cards in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCards {

        fragContext = context
        return ViewHolderCards(LayoutInflater.from(context).inflate(R.layout.cards_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderCards, position: Int) {
        holder?.bind(items[position]?.imgGold)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(items[position])
        }
    }

}

class ViewHolderCards (view: View) : RecyclerView.ViewHolder(view) {
    //val tvCardName = view.cardsList_card_name
    val imageView = view.cardsList_card_thumbnail

    fun bind(url: String?) {
        //Glide.with(CardsListAdapter.fragContext).load(url).into(imageView)

        val options = RequestOptions()
                .error(R.drawable.image_not_found)

        Glide.with(CardsListAdapter.fragContext)
                .load(url)
                .apply(options)
                .into(imageView)
    }
}