package com.example.lpiem.hearthstonecollectorapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.bumptech.glide.Glide
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_card_detail.*
import android.os.Build
import android.graphics.drawable.Drawable
import android.transition.Transition
import com.bumptech.glide.request.target.SimpleTarget



class CardDetailActivity : AppCompatActivity(), InterfaceCallBackDeck, InterfaceCallBackCard, InterfaceCallBackUser {
    var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        title = "Détail de la carte"
        val actionBar = supportActionBar
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val cardId = intent.getIntExtra("cardId", 0)
        println("cardId : "+cardId)


        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getCardById(cardId)

        btnEchange?.setOnClickListener({

        })

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

    override fun onWorkDeckDone(result: MutableList<Deck>) {  }

    override fun onWorkCardsDone(result: List<Card>) {   }

    override fun onWorkUserDone(result: List<User>) {   }

    override fun onWorkCardDone(result: List<Card>) {
        println(result)
        card = result[0]
        println(card!!.name)

        txtNom.text = card!!.name
        txtDescription.text = card!!.text
        txtCost.text = card!!.cost.toString()
        txtHealth.text = card!!.health.toString()
        txtAttack.text = card!!.attack.toString()

        Glide.with(this).load(card!!.img).into(imgCard)
    }
}
