package com.example.lpiem.hearthstonecollectorapp.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_deck_detail.*
import kotlinx.android.synthetic.main.dialog_edit_deck.*
import kotlinx.android.synthetic.main.toolbar_deck_detail.*

class DeckDetailActivity : AppCompatActivity(), InterfaceCallBackDeck, InterfaceCallBackCard, InterfaceCallBackUser {

    var deck: Deck? = null
    var cardsList: MutableList<Card>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_detail)

        val deckId = intent.getIntExtra("deckId", 0)
        println("deckId : $deckId")


        // SET ADAPTER NULL
//        var cardsAdapter = CardsListInDeckAdapter(emptyList<Card>(), this, object : CardsListInDeckAdapter.BtnClickListener {
//            override fun onBtnClick(position: Int, viewHolder: RecyclerView.ViewHolder) {   }
//            override fun onCardClick(position: Int) {   }
//        })
//        // Adapter et layout manager
//        rvCardsInDeckDetail.adapter = cardsAdapter //CardsListInDeckAdapter(this!!.cardsList!!, this)
//        rvCardsInDeckDetail.layoutManager = LinearLayoutManager(this)


        val controller = APIManager(this as InterfaceCallBackUser, this as InterfaceCallBackCard, null, null, this as InterfaceCallBackDeck)
        controller.getDeckById(deckId)

        // Gestion de la toolbar
        btn_back.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
        btn_edit.setOnClickListener {
            val builder = AlertDialog.Builder(this@DeckDetailActivity)
            builder.setTitle("Modifier le deck")
            val view = this.layoutInflater.inflate(R.layout.dialog_edit_deck, null)
            builder.setView(view)

            val titreEditText = view.findViewById<View>(R.id.dialogDeckNameEdit) as EditText
            val descriptionEditText = view.findViewById<View>(R.id.dialogDeckDescriptionEdit) as EditText

            titreEditText.setText(deck!!.name)
            descriptionEditText.setText(deck!!.description)

            builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
                val newName = titreEditText.text.toString()
                val newDescription = descriptionEditText.text.toString()
                println("new name : $newName")

                if (newName.isEmpty()) {
                    println("name is empty")
                    dialogDeckNameEdit?.error = "Titre vide"
                    Toast.makeText(applicationContext, "Titre vide : deck non ajouté",Toast.LENGTH_SHORT).show()

                } else if (newDescription.isEmpty()) {
                    println("description is empty")
                    dialogDeckDescriptionEdit?.error = "Description vide"
                    Toast.makeText(applicationContext, "Description vide : deck non ajouté",Toast.LENGTH_SHORT).show()

                } else {
                    println("modif ok")
                    deck!!.description = newDescription
                    deck!!.name = newName

                    txtDescription.text = deck!!.description
                    txtToolbarDeckDetail.text = deck!!.name

                    controller.updateDeck(deck!!)
                    dialog.dismiss()
                }
            }

                    .setNegativeButton(android.R.string.cancel) { dialog, p1 ->
                        dialog.cancel()
                    }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        btn_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this@DeckDetailActivity)
            builder.setTitle("Voulez-vous supprimer ce deck ?")
                    .setPositiveButton(R.string.positive_button){dialog, which ->
                        controller.deleteDeckById(deckId)
                     }
                    .setNegativeButton(R.string.cancel){dialog,which ->
                        dialog.cancel()
                    }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

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
        txtToolbarDeckDetail.text = deck!!.name
        txtDescription.text = deck!!.description
    }

    override fun onWorkDeleteDeckDone(result: JsonObject) {
        Toast.makeText(applicationContext, result.get("message").asString,Toast.LENGTH_SHORT).show()
        finish()
        overridePendingTransition(0, 0)
    }

    override fun onWorkDeckUpdatedDone(result: JsonObject) {
        println(result)
        Toast.makeText(applicationContext, result.get("message").asString,Toast.LENGTH_SHORT).show()
    }

    override fun onWorkCardsDone(result: List<Card>) {   }
    override fun onWorkUserDone(result: List<User>) {   }
    override fun onWorkAddDone(result: JsonObject) {   }
    override fun onWorkCardDone(result: List<Card>) {  }
    override fun onWorkDeckAddedDone(result: JsonObject) {   }

}
