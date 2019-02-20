package com.example.lpiem.hearthstonecollectorapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_deck_detail.*

class DeckDetailActivity : AppCompatActivity(), InterfaceCallBackDeck, InterfaceCallBackCard, InterfaceCallBackUser {
    var deck: Deck? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_detail)
        val actionBar = supportActionBar
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val deckId = intent.getIntExtra("deckId", 0)
        println("deckId : "+deckId)

        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getDeckById(deckId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                overridePendingTransition(0, 0)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWorkDecksDone(result: MutableList<Deck>) {  }

    override fun onWorkDeckDone(result: List<Deck>) {
        println(result)
        deck = result[0]
        println(deck!!.name)

       title = deck!!.name
        txtDescription.text = deck!!.description
    }

    override fun onWorkCardsDone(result: List<Card>) {   }

    override fun onWorkUserDone(result: List<User>) {   }

    override fun onWorkCardDone(result: List<Card>) {  }
}
