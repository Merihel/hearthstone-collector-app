package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.lpiem.hearthstonecollectorapp.Fragments.CardsListFragment
import com.example.lpiem.hearthstonecollectorapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.nav_header_navigation.*
import kotlinx.android.synthetic.main.nav_header_navigation.view.*
import org.json.JSONObject

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal lateinit var response: JSONObject
    internal lateinit var profile_pic_data:JSONObject
    internal lateinit var profile_pic_url:JSONObject

    private var drawerLayout: DrawerLayout? = null

    private val decksFragment: Fragment? = null
    private val tradeFragment: Fragment? = null
    private val quizzFragment: Fragment? = null
    private val shopFragment: Fragment? = null

    private val FRAGMENT_CARDSLIST = 0
    private val FRAGMENT_DECKS = 1
    private val FRAGMENT_TRADE = 2
    private val FRAGMENT_QUIZZ = 3
    private val FRAGMENT_SHOP = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val intent = intent
        val jsondata = intent.getStringExtra("userProfile")
        Log.w("Jsondata", jsondata)
        val headerView = nav_view.getHeaderView(0)

        try {
            response = JSONObject(jsondata)
            Log.e("[NavigationActivity]", response.get("name").toString())
            headerView.nameUser.text = response.get("name").toString()

            if (!response.isNull("pictureUrlGoogle") && response.has("pictureUrlGoogle")) {
                println("[NavigationActivity] url picture google ok")
                profile_pic_url = JSONObject()
                profile_pic_url.put("url", response.getString("pictureUrlGoogle"))
            } else {
                profile_pic_data = JSONObject(response.get("picture").toString())
                profile_pic_url = JSONObject(profile_pic_data.getString("data"))
            }

            //Picasso.with(this).load(profile_pic_url.getString("url")).into(imgUser)

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item
        // clicks here.
        when (item.itemId) {
            R.id.nav_cards -> {
                Log.d("[NavigationActivity]", "itemSelected !")
                val fragment = CardsListFragment.newInstance()
                replaceFragment(fragment)
            }
            R.id.nav_decks -> {
                //showFragment(new DecksFragment());
            }
            R.id.nav_trade -> {
                // showFragment(new TradeFragment());
            }
            R.id.nav_quizz -> {
                //showFragment(new QuizzFragment());
            }
            R.id.nav_shop -> {
                //showFragment(new ShopFragment());
            }
            R.id.nav_deconnexion -> {
                val intent = Intent(this@NavigationActivity, ConnexionActivity::class.java)
                intent.putExtra("deconnexion", true)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        Log.d("[NavigationActivity]", "passe dans replaceFragment !")
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.drawer_layout, fragment)
        fragmentTransaction.commit()
    }


}
