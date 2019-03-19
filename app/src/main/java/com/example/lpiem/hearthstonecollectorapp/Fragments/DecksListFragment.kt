package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Activities.NavigationActivity
import com.example.lpiem.hearthstonecollectorapp.Adapter.DecksListAdapter
import com.example.lpiem.hearthstonecollectorapp.Adapter.SwipeToDeleteCallback
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackDeck
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.Models.User
import kotlinx.android.synthetic.main.dialog_edit_deck.*
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_decks_list.*
import kotlinx.android.synthetic.main.toolbar.view.*
import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.lpiem.hearthstonecollectorapp.Activities.DeckDetailActivity
import com.google.gson.JsonObject


@SuppressLint("StaticFieldLeak")
private var rootView: View? = null
private var lManager: androidx.recyclerview.widget.LinearLayoutManager? = null

class DecksListFragment : Fragment(), InterfaceCallBackDeck, InterfaceCallBackUser, InterfaceCallBackCard {
    private var decks = emptyList<Deck>()
    private var decks2 = mutableListOf<Deck>()
    private lateinit var adapter : DecksListAdapter

    companion object {
        fun newInstance(): CardsListFragment {
            System.out.println("new instance decks list")
            return CardsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_decks_list, container, false)
        return rootView
    }

    override fun onResume() {
        println("on resume deck list")
        super.onResume()
        val controller = APIManager(this as InterfaceCallBackUser, this as InterfaceCallBackCard, null, null, this as InterfaceCallBackDeck)
        controller.getDecksByUser(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = APIManager(this as InterfaceCallBackUser, this as InterfaceCallBackCard, null, null, this as InterfaceCallBackDeck)

        // Gestion de la toolbar
        decks_toolbar.tvTitre.text = "Mes decks"
        decks_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }

        // Création d'un deck
        decks_toolbar.ic_add.setOnClickListener {
            val builder = AlertDialog.Builder(this!!.activity!!)
            builder.setTitle("Créer un deck")
            val view = this.layoutInflater.inflate(R.layout.dialog_edit_deck, null)
            builder.setView(view)

            val titreEditText = view.findViewById<View>(R.id.dialogDeckNameEdit) as EditText
            val descriptionEditText = view.findViewById<View>(R.id.dialogDeckDescriptionEdit) as EditText

            builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
                val newName = titreEditText.text
                val newDescription = descriptionEditText.text

                if (newName.isNullOrEmpty()) {
                    println("name is empty")
                    dialogDeckNameEdit.error = "Titre vide"//getString(R.string.validation_empty)
                }

                else if (newDescription.isNullOrEmpty()) {
                    println("description is empty")
                    dialogDeckDescriptionEdit.error = "Description vide"//getString(R.string.validation_empty)
                }

                else {
                    println("création du deck ok")
                    val deck = Deck(null, newName.toString(), newDescription.toString())
                    controller.createDeck(deck)
                    dialog.dismiss()
                }
            }

                    .setNegativeButton(android.R.string.cancel) { dialog, p1 ->
                        dialog.cancel()
                    }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        // Adapter et layout manager
        val listener = object : DecksListAdapter.Listener {
            override fun onItemClicked(item: Deck) {
                val intent = Intent(activity, DeckDetailActivity::class.java)
                intent.putExtra("deckId", item.id)
                activity!!.startActivity(intent)
            }
        }
        adapter = DecksListAdapter(decks2, activity!!.applicationContext, listener)
        recycler_view_decks.layoutManager = LinearLayoutManager(context)
        recycler_view_decks.adapter = adapter

        // Gestion du swipe à gauche pour la suppression
        val swipeHandler = object : SwipeToDeleteCallback(this.context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                controller.deleteDeckById(adapter.items.get(viewHolder.adapterPosition).id)
                val adapter = recycler_view_decks.adapter as DecksListAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view_decks)

    }

    override fun onWorkDecksDone(result: MutableList<Deck>) {
        System.out.println("My user decks" + result.toString())
        decks = result

        decks2.clear()
        decks2.addAll(result)

        adapter.setData(result)
    }

    override fun onWorkUserDone(result: List<User>) {   }
    override fun onWorkCardDone(result: List<Card>) {   }
    override fun onWorkCardsDone(result: List<Card>) {   }
    override fun onWorkDeckDone(result: List<Deck>) {   }
    override fun onWorkDeckUpdatedDone(result: JsonObject) {   }

    override fun onWorkDeleteDeckDone(result: JsonObject) {
        Toast.makeText(context, result.get("message").asString,Toast.LENGTH_SHORT).show()
    }

    override fun onWorkDeckAddedDone(result: JsonObject) {
        println("on work deck added done : result id"+result.get("id").asInt)
        Toast.makeText(context, "Deck créée",Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, DeckDetailActivity::class.java)
        intent.putExtra("deckId", result.get("id").asInt)
        activity!!.startActivity(intent)
    }

}
