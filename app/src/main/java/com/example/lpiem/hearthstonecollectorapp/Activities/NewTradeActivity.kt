package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.lpiem.hearthstonecollectorapp.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lpiem.hearthstonecollectorapp.Adapter.CardsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackFriendship
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.android.synthetic.main.activity_new_trade.*

class NewTradeActivity : AppCompatActivity(), InterfaceCallBackFriendship, InterfaceCallBackCard, AdapterView.OnItemSelectedListener {

    //Amis et adapters du spinner
    var listFriends : ArrayList<Friendship> = arrayListOf()
    var spinnerFriends: Spinner? = null
    var aa: ArrayAdapter<Friendship>? = null

    //Cards à echanger avec user : ces trois doivent être != null pour lancer le trade
    var selectedForTrade: Card? = null
    var withUser: User? = null
    var wantedForTrade: Card? = null

    //Autres
    private var hsUserManager = HsUserManager
    private val controller = APIManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trade)

        spinnerFriends = findViewById(R.id.new_trade_spinner_friends)
        spinnerFriends!!.onItemSelectedListener = this

        //ArrayAdapter
        aa = ArrayAdapter<Friendship>(this, android.R.layout.simple_spinner_item, listFriends)
        // Set layout to use when the list of choices appear
        aa!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerFriends!!.adapter = aa

        controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!,this)
        val intent = intent
        val cardId = intent.getIntExtra("selectedCard", 0)
        Log.d("CARDID", cardId.toString())
        controller.getCardById(cardId, this)

        new_trade_btn_execute.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                if (selectedForTrade != null && wantedForTrade != null && withUser != null) {
                    Log.d("NewTrade", "New trade possible, j'échange "+selectedForTrade!!.name+" avec "+withUser!!.pseudo+" pour la carte "+wantedForTrade!!.name)
                } else {
                    Log.e("NODATA", "Echange impossible, il manque des données")
                    Toast.makeText(applicationContext, "Vous devez choisir un ami et une carte de son inventaire à lui demander", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        wantedForTrade = null
        new_trade_card_selected.text = "Choisissez une carte d'échange"
        new_trade_card_selected.setTextColor(resources.getColor(R.color.colorOrangeFriend))
        if(position != 0) {
            withUser = listFriends.get(position).user2
            val listener = object : CardsListAdapter.Listener {
                override fun onItemClicked(item: Card) {
                    Log.d("CardToReceive", item.name)
                    wantedForTrade = item
                    new_trade_card_selected.text = wantedForTrade!!.name
                    new_trade_card_selected.setTextColor(resources.getColor(R.color.colorGreenFriend))
                }
            }
            new_trade_recycler_view.adapter = CardsListAdapter(withUser!!.cards!!, this, listener)
            new_trade_recycler_view.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        } else {
            withUser = null
        }

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    //Friendships methods

    override fun onFriendshipDone(result: List<Friendship>) {
        if (result.isNotEmpty()) {
            Log.d("onFriendshipDone", result[0].id.toString())
            listFriends.add(Friendship(0, User(0, "NONE"), User(0, "Choisissez un ami..."), true, 0))
            for (friendship in result) {
                listFriends.add(friendship)
            }
            aa!!.notifyDataSetChanged()

        }
    }
    override fun onPendingFriendshipDone(result: List<Friendship>) {
    }
    override fun onDeleteDone(result: JsonObject) {
    }
    override fun onAddDone(result: JsonObject) {
    }
    override fun onAcceptDone(result: JsonObject) {
    }

    override fun onWorkCardDone(result: List<Card>) {
        selectedForTrade = result.get(0)
        Glide.with(this).load(selectedForTrade!!.img).into(new_trade_selected_card)
    }

    override fun onWorkCardsDone(result: List<Card>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
