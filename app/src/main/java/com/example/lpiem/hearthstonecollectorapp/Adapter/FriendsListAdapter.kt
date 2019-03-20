package com.example.lpiem.hearthstonecollectorapp.Adapter;

import android.content.Context
import android.graphics.Typeface
import android.opengl.Visibility
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.example.lpiem.hearthstonecollectorapp.R
import com.example.lpiem.hearthstonecollectorapp.Models.User
import kotlinx.android.synthetic.main.friends_list_item.view.*

private var hsUserManager = HsUserManager

class FriendsListAdapter(val items: List<Friendship>, val context: Context, var listener: Listener) : RecyclerView.Adapter<ViewHolderFriends>() {

    companion object {
        lateinit var fragContext: Context
    }

    interface Listener {
        fun onItemClicked(item: Friendship)
        fun onDeleteClicked(item: Friendship)
        fun onAcceptClicked(item: Friendship)
    }

    // Gets the number of friends in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFriends {
        fragContext = context
        return ViewHolderFriends(LayoutInflater.from(context).inflate(R.layout.friends_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderFriends, position: Int) {
        holder?.bind(items[position])
        holder.itemView.setOnClickListener {
            listener.onItemClicked(items[position])
        }
        holder.imageView.setOnClickListener {
            listener.onDeleteClicked(items[position])
        }
        holder.acceptButton.setOnClickListener{
            Log.d("AcceptFriendship", "Prêt à accepter une nouvelle amitié proposée par "+items[position].user2)
            listener.onAcceptClicked(items[position])
        }
    }
}

class ViewHolderFriends (view: View) : RecyclerView.ViewHolder(view) {
    //val tvCardName = view.cardsList_card_name
    val textView = view.friends_list_item_pseudo
    val imageView = view.btn_delete_friend
    val acceptButton = view.btn_accept_friend
    val cont = view.context

    fun bind(friendship: Friendship) {
        textView?.text = friendship.user2.pseudo
        if (friendship.isAccepted) {
            textView.setTextColor(ContextCompat.getColor(cont, R.color.colorGreenFriend))
        } else {
            if(hsUserManager.loggedUser.id != friendship.whoDemanding) {
                Log.d("Visible?", "On le set à VISIBLEEEEE")
                acceptButton.visibility = View.VISIBLE
                textView.setTextColor(ContextCompat.getColor(cont, R.color.colorOrangeFriend))
            } else {
                textView.setTextColor(ContextCompat.getColor(cont, R.color.colorRedFriend))
                textView.setTypeface(null, Typeface.ITALIC)
            }
        }
    }
}