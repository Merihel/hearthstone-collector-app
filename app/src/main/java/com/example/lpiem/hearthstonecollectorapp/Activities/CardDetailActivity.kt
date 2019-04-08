package com.example.lpiem.hearthstonecollectorapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_card_detail.*


class CardDetailActivity : AppCompatActivity(), InterfaceCallBackCard {
    var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        title = getString(R.string.card_detail_navbar)
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val cardId = intent.getIntExtra("cardId", 0)
        val controller = APIManager()
        controller.getCardById(cardId, this)

        controller.getCardById2(cardId, this).observe(this, Observer {
            println(it)
            card = it[0]

            txtNom.text = card!!.name
            txtCost.text = card!!.cost.toString()
            txtHealth.text = card!!.health.toString()
            txtAttack.text = card!!.attack.toString()
            lblDescription.text = Html.fromHtml(card!!.text)
            Glide.with(this).load("https://art.hearthstonejson.com/v1/orig/"+card!!.hsId+".png").into(imgCard)
        })

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

    override fun onWorkCardsDone(result: List<Card>) {   }
    override fun onWorkCardDone(result: List<Card>) {
        if (!this.isFinishing) {
            println(result)
            card = result[0]
            println(card!!.name)

            txtNom.text = card!!.name
            txtCost.text = card!!.cost.toString()
            txtHealth.text = card!!.health.toString()
            txtAttack.text = card!!.attack.toString()
            lblDescription.text = Html.fromHtml(card!!.text)
            Glide.with(this).load("https://art.hearthstonejson.com/v1/orig/" + card!!.hsId + ".png").into(imgCard)
        }
    }
}
