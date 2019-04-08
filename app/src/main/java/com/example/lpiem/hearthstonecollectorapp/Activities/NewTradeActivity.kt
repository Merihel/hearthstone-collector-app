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
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.lpiem.hearthstonecollectorapp.Adapter.CardsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.example.lpiem.hearthstonecollectorapp.Models.User
import kotlinx.android.synthetic.main.activity_new_trade.*

class NewTradeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //Amis et adapters du spinner
    private var listFriends : ArrayList<Friendship> = arrayListOf()
    private var spinnerFriends: Spinner? = null
    private var aa: ArrayAdapter<Friendship>? = null

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

        //controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!)
        controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
            if (it.isNotEmpty()) {
                Log.d("onFriendshipDone", it[0].id.toString())
                listFriends.add(Friendship(0, User(0, "NONE"), User(0, "Choisissez un ami..."), true, 0))
                for (friendship in it) {
                    listFriends.add(friendship)
                }
                aa!!.notifyDataSetChanged()
            }
        })

        val intent = intent
        val cardId = intent.getIntExtra("selectedCard", 0)
        Log.d("CARDID", cardId.toString())

        //TODO MUTABLE LIVE DATA
        controller.getCardById(cardId).observe(this, Observer {
            selectedForTrade = it[0]
            Glide.with(this).load(selectedForTrade!!.img).into(new_trade_selected_card)
        })

        new_trade_btn_execute.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                if (selectedForTrade != null && wantedForTrade != null && withUser != null) {
                    executeNewTrade()
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

    fun executeNewTrade() {
        controller.newTrade(hsUserManager.loggedUser.id!!, withUser!!.id!!, selectedForTrade!!.id, wantedForTrade!!.id, "PENDING", true, false).observe(this, Observer {
            if (it != null) {
                Log.d("NewTradeResponse", it.get("devMessage").asString)
                Toast.makeText(this, it.get("message").asString, Toast.LENGTH_LONG).show()
                var intent = Intent(this@NewTradeActivity, NavigationActivity::class.java)
                startActivity(intent)
            } else {
                Log.e("NewTradeResponse", "'it' is null")
            }
        })
    }

}
