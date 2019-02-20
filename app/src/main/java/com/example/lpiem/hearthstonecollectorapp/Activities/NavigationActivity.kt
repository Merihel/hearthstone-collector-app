package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import android.widget.FrameLayout
import com.example.lpiem.hearthstonecollectorapp.Fragments.CardsListFragment
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Fragments.DecksListFragment
import com.example.lpiem.hearthstonecollectorapp.R
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.nav_header_navigation.view.*
import org.json.JSONObject

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal lateinit var response: JSONObject
    internal lateinit var profile_pic_data:JSONObject
    internal lateinit var profile_pic_url:JSONObject

    private var hsUserManager = HsUserManager

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

        Log.d("InNav-User", hsUserManager.loggedUser.toString())
        Log.d("InNav-UserSocialInfos", hsUserManager.userSocialInfos.toString())

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val headerView = nav_view.getHeaderView(0)

        try {
            Log.d("[NavigationActivity]", hsUserManager.loggedUser.pseudo)
            headerView.nameUser.text = hsUserManager.loggedUser.pseudo

            if (hsUserManager.userSocialInfos.isNull("email")) {
                Log.d("NavigationActivity", "No social email, add default avatar")
                setHeaderAvatar(hsUserManager.defautThumbnail)
            } else {
                if (hsUserManager.userSocialInfos.get("email") != null) {
                    if (hsUserManager.userSocialInfos.get("type") == "f") { //On est connecté via Facebook
                        if (hsUserManager.userSocialInfos.get("picture") != null) { //Récupérer l'image de FB
                            var json = hsUserManager.userSocialInfos.get("picture") as JSONObject
                            var data = json.get("data") as JSONObject
                            var url = data.get("url") as String
                            Log.d("Facebook Image URL", url)
                            setHeaderAvatar(url)
                        } else {
                            setHeaderAvatar(hsUserManager.defautThumbnail)
                        }
                    } else if (hsUserManager.userSocialInfos.get("type") == "g") {
                        if (hsUserManager.userSocialInfos.get("picture") != null) { //Récupérer l'image de google
                            var url = hsUserManager.userSocialInfos.get("picture") as Uri
                            Log.d("Google Image URL", url.toString())
                            setHeaderAvatar(url.toString())
                            //Picasso.with(this).load(url).into(imgUser)
                        } else {
                            setHeaderAvatar(hsUserManager.defautThumbnail)
                        }
                    }
                }
            }

            nav_view.getHeaderView(0).moneyUser.text = hsUserManager.loggedUser.coins.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Fragment par défaut : liste des cartes
        val fragment = CardsListFragment.newInstance()
        replaceFragment(fragment)
        nav_view.getMenu().getItem(0).setChecked(true)
    }

    fun setHeaderAvatar(image: String) {
        Glide.with(this).load(image).into(nav_view.getHeaderView(0).imgUser)
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
