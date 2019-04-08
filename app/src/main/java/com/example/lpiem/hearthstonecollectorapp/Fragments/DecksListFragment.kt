package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lpiem.hearthstonecollectorapp.Activities.DeckDetailActivity
import com.example.lpiem.hearthstonecollectorapp.Activities.NavigationActivity
import com.example.lpiem.hearthstonecollectorapp.Adapter.DecksListAdapter
import com.example.lpiem.hearthstonecollectorapp.Adapter.SwipeToDeleteCallback
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.dialog_edit_deck.*
import kotlinx.android.synthetic.main.fragment_decks_list.*
import kotlinx.android.synthetic.main.toolbar.view.*


@SuppressLint("StaticFieldLeak")
private var rootView: View? = null

class DecksListFragment : Fragment() {
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
        super.onResume()
        val controller = APIManager()
        controller.getDecksByUser(HsUserManager.loggedUser.id!!).observe(this, Observer {
            System.out.println("My user decks" + it.toString())
            decks = it
            decks2.clear()
            decks2.addAll(it)
            adapter.setData(it as MutableList<Deck>)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = APIManager()

        // Gestion de la toolbar
        decks_toolbar.tvTitre.text = "Mes decks"
        decks_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }

        // Création d'un deck
        decks_toolbar.ic_add.setOnClickListener {
            val builder = AlertDialog.Builder(this.activity!!)
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
                    val deck = Deck(null, newName.toString(), newDescription.toString(), null, HsUserManager.loggedUser)
                    controller.createDeck(deck).observe(this, Observer {
                        println("on work deck added done : result id"+it.get("id").asInt)
                        Toast.makeText(context, "Deck créée",Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, DeckDetailActivity::class.java)
                        intent.putExtra("deckId", it.get("id").asInt)
                        activity!!.startActivity(intent)
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
                controller.deleteDeckById(adapter.items.get(viewHolder.adapterPosition).id!!).observe(this@DecksListFragment, Observer {
                    Toast.makeText(context, it.get("message").asString,Toast.LENGTH_SHORT).show()
                })
                val adapter = recycler_view_decks.adapter as DecksListAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view_decks)
    }

}
