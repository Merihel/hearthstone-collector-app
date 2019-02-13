package com.example.lpiem.hearthstonecollectorapp.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.list_deck_item.view.*

class DecksListAdapter (var items: MutableList<Deck>, val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    companion object {
        lateinit var fragContext: Context
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(items: MutableList<Deck>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        println("remove at")
        items.removeAt(position)
        notifyItemRemoved(position)

        // supprimer dans la base
        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        //controller.deleteDeckByUser(1, 1)
    }

    //Inflates the item view - p0=parent, p1=viewType
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        fragContext = context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_deck_item, p0, false))
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder?.bind(items.get(position).name!!)
//        p0.itemView.setOnClickListener { v ->
//            mListener?.onItemClickListener(v, holder.layoutPosition)
//        }

        if(position %2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#998341"));
            viewHolder.itemView.tv_deck_name.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvDeckName = view.tv_deck_name

    fun bind(name: String) {
        tvDeckName.text = name
    }

}