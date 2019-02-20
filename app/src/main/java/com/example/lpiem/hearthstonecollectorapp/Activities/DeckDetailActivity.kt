package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Adapter.CardsListInDeckAdapter
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_deck_detail.*
import kotlinx.android.synthetic.main.toolbar_deck_detail.*

class DeckDetailActivity : AppCompatActivity(), InterfaceCallBackDeck, InterfaceCallBackCard, InterfaceCallBackUser {
    var deck: Deck? = null
    var cardsList: MutableList<Card>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_detail)
        //this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val deckId = intent.getIntExtra("deckId", 0)
        println("deckId : "+deckId)


        // SET ADAPTER NULL
//        var cardsAdapter = CardsListInDeckAdapter(emptyList<Card>(), this, object : CardsListInDeckAdapter.BtnClickListener {
//            override fun onBtnClick(position: Int, viewHolder: RecyclerView.ViewHolder) {   }
//            override fun onCardClick(position: Int) {   }
//        })
//        // Adapter et layout manager
//        rvCardsInDeckDetail.adapter = cardsAdapter //CardsListInDeckAdapter(this!!.cardsList!!, this)
//        rvCardsInDeckDetail.layoutManager = LinearLayoutManager(this)


        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getDeckById(deckId)

        // Gestion de la toolbar
//        decks_toolbar.ic_menu.setOnClickListener {
//            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
//        }
//        decks_toolbar.ic_add.setOnClickListener {
//            // TODO: suppression + modification de deck
//        }
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
        deck = result[0]
        cardsList = result[0].cardsList?.toMutableList()

        // ON CLICK
        var cardsAdapter = CardsListInDeckAdapter(this.cardsList!!, this, object : CardsListInDeckAdapter.BtnClickListener {
            override fun onBtnClick(position: Int, viewHolder: RecyclerView.ViewHolder) {
                Toast.makeText(applicationContext, "Carte supprimée",Toast.LENGTH_SHORT).show()
                val adapter = rvCardsInDeckDetail.adapter as CardsListInDeckAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }

            override fun onCardClick(position: Int) {
                val intent = Intent(this@DeckDetailActivity, CardDetailActivity::class.java)
                intent.putExtra("cardId", cardsList!![position].id)
                startActivityForResult(intent, 0)
            }
        })

        // Adapter et layout manager
        rvCardsInDeckDetail.adapter = cardsAdapter //CardsListInDeckAdapter(this!!.cardsList!!, this)
        rvCardsInDeckDetail.layoutManager = LinearLayoutManager(this)

        println(deck)
        println("cards list : "+ cardsList!!)
        title = deck!!.name
        txtDescription.text = deck!!.description
    }

    override fun onWorkCardsDone(result: List<Card>) {   }

    override fun onWorkUserDone(result: List<User>) {   }

    override fun onWorkCardDone(result: List<Card>) {  }
}
