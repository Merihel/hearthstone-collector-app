package com.example.lpiem.hearthstonecollectorapp.Activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.example.lpiem.hearthstonecollectorapp.Fragments.CardsListFragment
import com.example.lpiem.hearthstonecollectorapp.Fragments.DecksListFragment
import com.example.lpiem.hearthstonecollectorapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import org.json.JSONObject

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal lateinit var response: JSONObject
    internal lateinit var profile_pic_data:JSONObject
    internal lateinit var profile_pic_url:JSONObject

    private var drawerLayout: androidx.drawerlayout.widget.DrawerLayout? = null
    private var content: FrameLayout? = null

    private val decksFragment: androidx.fragment.app.Fragment? = null
    private val tradeFragment: androidx.fragment.app.Fragment? = null
    private val quizzFragment: androidx.fragment.app.Fragment? = null
    private val shopFragment: androidx.fragment.app.Fragment? = null

    private val FRAGMENT_CARDSLIST = 0
    private val FRAGMENT_DECKS = 1
    private val FRAGMENT_TRADE = 2
    private val FRAGMENT_QUIZZ = 3
    private val FRAGMENT_SHOP = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        content = findViewById(R.id.content_navigation) as FrameLayout

//        val toggle = ActionBarDrawerToggle(
//                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

//        val intent = intent
//        val jsondata = intent.getStringExtra("userProfile")
//        Log.w("Jsondata", jsondata)
//        val headerView = navigationView?.getHeaderView(0)
//
//        try {
//            response = JSONObject(jsondata)
//            Log.e("[NavigationActivity]", response.get("name").toString())
//            nameUser.text = response.get("name").toString()
//
//            if (!response.isNull("pictureUrlGoogle") && response.has("pictureUrlGoogle")) {
//                println("[NavigationActivity] url picture google ok")
//                profile_pic_url = JSONObject()
//                profile_pic_url.put("url", response.getString("pictureUrlGoogle"))
//            } else {
//                profile_pic_data = JSONObject(response.get("picture").toString())
//                profile_pic_url = JSONObject(profile_pic_data.getString("data"))
//            }
//
//            Picasso.with(this).load(profile_pic_url.getString("url")).into(imgUser)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }


        // Fragment par défaut : liste des cartes
        val fragment = CardsListFragment()
        replaceFragment(fragment)
        nav_view.menu.getItem(0).setChecked(true)


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
        when (item.itemId) {
            R.id.nav_cards -> {
                val fragment = CardsListFragment() //.newInstance()
                replaceFragment(fragment)
            }
            R.id.nav_decks -> {
                val fragment = DecksListFragment() //.newInstance()
                replaceFragment(fragment)
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
                val intent = ConnexionActivity.newIntent(this, true)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_navigation, fragment, fragment.javaClass.getSimpleName())
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }

}
