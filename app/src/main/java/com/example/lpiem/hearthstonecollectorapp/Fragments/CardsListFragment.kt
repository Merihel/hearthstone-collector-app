package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.lpiem.hearthstonecollectorapp.Activities.CardDetailActivity
import com.example.lpiem.hearthstonecollectorapp.Activities.NavigationActivity
import com.example.lpiem.hearthstonecollectorapp.Adapter.CardsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_cards_list.*
import kotlinx.android.synthetic.main.toolbar.view.*


private var rootView: View? = null
private var hsUserManager = HsUserManager

class CardsListFragment :  Fragment() {
    companion object {
        fun newInstance(): CardsListFragment {
            System.out.println("new instance cards list")
            return CardsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cards_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gestion de la toolbar
        cards_toolbar.tvTitre.text = "Liste de cartes"
        cards_toolbar.ic_add.visibility = View.GONE

        cards_toolbar.ic_menu.setOnClickListener {
            ((activity) as NavigationActivity).drawer_layout.openDrawer(GravityCompat.START)
        }

        val controller = APIManager()
        controller.getCardsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
            System.out.println("My user cards" + it.toString())
            val listener = object : CardsListAdapter.Listener {
                override fun onItemClicked(item: Card) {
                    val intent = Intent(activity, CardDetailActivity::class.java)
                    intent.putExtra("cardId", item.id)
                    activity!!.startActivity(intent)
                }
            }
            rv_cards_list.adapter = CardsListAdapter(it, getActivity()!!.applicationContext, listener)
            rv_cards_list.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
            //Need to push the cards to the RecyclerView
        })
    }

}
