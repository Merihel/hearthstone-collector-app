package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Adapter.CardsListInDeckAdapter
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_deck_detail.*
import kotlinx.android.synthetic.main.dialog_edit_deck.*
import kotlinx.android.synthetic.main.toolbar_deck_detail.*

class DeckDetailActivity : AppCompatActivity() {

    var deck: Deck? = null
    var cardsList: MutableList<Card>? = null
    val controller = APIManager()


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


        controller.getDeckById(deckId).observe(this, Observer {
            deck = it[0]
            cardsList = it[0].cardsList?.toMutableList()
            deck!!.cardsList = it[0].cardsList

            // ON CLICK
            val cardsAdapter = CardsListInDeckAdapter(this.cardsList!!, this, object : CardsListInDeckAdapter.BtnClickListener {
                override fun onBtnClick(position: Int, viewHolder: RecyclerView.ViewHolder) {
                    val adapter = rvCardsInDeckDetail.adapter as CardsListInDeckAdapter
                    adapter.removeAt(viewHolder.adapterPosition)

                    //deck!!.cardsList!!.toMutableList().removeAt(viewHolder.adapterPosition)
                    // deck!!.cardsList = adapter.items.toTypedArray()
                    //  controller.updateDeck(deck!!)
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
        })


        // Gestion de la toolbar
        btn_back.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
        btn_edit.setOnClickListener {
            val builder = AlertDialog.Builder(this@DeckDetailActivity)
            builder.setTitle(getString(R.string.card_detail_navbar))
            val view = this.layoutInflater.inflate(R.layout.dialog_edit_deck, null)
            builder.setView(view)

            val titreEditText = view.findViewById<View>(R.id.dialogDeckNameEdit) as EditText
            val descriptionEditText = view.findViewById<View>(R.id.dialogDeckDescriptionEdit) as EditText

            titreEditText.setText(deck!!.name)
            descriptionEditText.setText(deck!!.description)

            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                val newName = titreEditText.text.toString()
                val newDescription = descriptionEditText.text.toString()

                if (newName.isEmpty()) {
                    dialogDeckNameEdit?.error = "Titre vide"
                    Toast.makeText(applicationContext, "Titre vide : deck non ajouté",Toast.LENGTH_SHORT).show()

                } else if (newDescription.isEmpty()) {
                    dialogDeckDescriptionEdit?.error = "Description vide"
                    Toast.makeText(applicationContext, "Description vide : deck non ajouté",Toast.LENGTH_SHORT).show()

                } else {
                    deck!!.description = newDescription
                    deck!!.name = newName

                    txtDescription.text = deck!!.description
                    txtToolbarDeckDetail.text = deck!!.name

                    controller.updateDeck(deck!!).observe(this, Observer {
                        println(it)
                        Toast.makeText(applicationContext, it.get("message").asString,Toast.LENGTH_SHORT).show()
                    })
                    dialog.dismiss()
                }
            }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        btn_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this@DeckDetailActivity)
            builder.setTitle(getString(R.string.deck_detail_delete_deck))
                    .setPositiveButton(R.string.positive_button){ _, _ ->
                        controller.deleteDeckById(deckId).observe(this, Observer {
                            Toast.makeText(applicationContext, it.get("message").asString,Toast.LENGTH_SHORT).show()
                            finish()
                            overridePendingTransition(0, 0)
                        })
                     }
                    .setNegativeButton(R.string.cancel){ dialog, _ ->
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

}
