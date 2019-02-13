package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
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

import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_decks_list.*
import kotlinx.android.synthetic.main.toolbar.view.*
import android.content.DialogInterface
import android.graphics.Color


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gestion de la toolbar
        decks_toolbar.tvTitre.text = "Mes decks"
        decks_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }
        decks_toolbar.ic_add.setOnClickListener {
            // TODO: ajout de deck
        }

        // Adapter et layout manager
        adapter = DecksListAdapter(decks2, activity!!.applicationContext)
        recycler_view_decks.layoutManager = LinearLayoutManager(context)
        recycler_view_decks.adapter = adapter

        // Gestion du swipe à gauche pour la suppression
        val swipeHandler = object : SwipeToDeleteCallback(this.context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val builder = AlertDialog.Builder(activity)
                        .setTitle("Voulez-vous supprimer ce deck ?")
                        .setPositiveButton("Oui") { _, _ ->
                            Toast.makeText(context, "Deck supprimé",Toast.LENGTH_SHORT).show()
                            val adapter = recycler_view_decks.adapter as DecksListAdapter
                            adapter.removeAt(viewHolder.adapterPosition)
                        }
                        .setNegativeButton("Non") { _, _ ->
                            // enlever le fond rouge du SwipeToDeleteCallback
                        }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                negativeButton.setBackgroundColor(Color.TRANSPARENT)
                val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                positiveButton.setBackgroundColor(Color.TRANSPARENT)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view_decks)


        val controller = APIManager(this as InterfaceCallBackDeck, this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getDecksByUser(1)
    }

    override fun onWorkDeckDone(result: MutableList<Deck>) {
        System.out.println("My user decks" + result.toString())
        decks = result

        decks2.clear()
        decks2.addAll(result)

        adapter.setData(result)
    }

    override fun onWorkUserDone(result: List<User>) {
    }

    override fun onWorkCardDone(result: List<Card>) {
    }

    override fun onWorkCardsDone(result: List<Card>) {
    }

}
