package com.example.lpiem.hearthstonecollectorapp.Adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.fragment_pending_friend_list.view.*
import kotlinx.android.synthetic.main.friends_list_item.view.*

class PendingFriendsListAdapter(val items: List<Friendship>, val context: Context, var listener: Listener) : RecyclerView.Adapter<PendingViewHolderFriends>() {

    companion object {
        lateinit var fragContext: Context
    }

    interface Listener {
        fun onItemClicked(item: Friendship)
        fun onDeleteClicked(item: Friendship)
    }

    // Gets the number of friends in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolderFriends {
        fragContext = context
        return PendingViewHolderFriends(LayoutInflater.from(context).inflate(R.layout.fragment_pending_friend_list, parent, false))
    }

    override fun onBindViewHolder(holder: PendingViewHolderFriends, position: Int) {
        holder.bind(items[position].user2.pseudo)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(items[position])
        }
        holder.imageView.setOnClickListener {
            listener.onDeleteClicked(items[position])
        }
    }
}

class PendingViewHolderFriends (view: View) : RecyclerView.ViewHolder(view) {
    //val tvCardName = view.cardsList_card_name
    val recyclerView = view.rv_pending_friends_list
    val textView = recyclerView.friends_list_item_pseudo
    val imageView = view.btn_delete_friend

    fun bind(pseudo: String?) {
        textView?.text = pseudo
    }
}