package com.example.lpiem.hearthstonecollectorapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpiem.hearthstonecollectorapp.Fragments.CardsListFragment;
import com.example.lpiem.hearthstonecollectorapp.R;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    JSONObject response, profile_pic_data, profile_pic_url;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment cardsListFragment;
    private Fragment decksFragment;
    private Fragment tradeFragment;
    private Fragment quizzFragment;
    private Fragment shopFragment;

    private static final int FRAGMENT_CARDSLIST = 0;
    private static final int FRAGMENT_DECKS = 1;
    private static final int FRAGMENT_TRADE = 2;
    private static final int FRAGMENT_QUIZZ = 3;
    private static final int FRAGMENT_SHOP = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("userProfile");
        Log.w("Jsondata", jsondata);
        View headerView = navigationView.getHeaderView(0);
        TextView user_name = (TextView) headerView.findViewById(R.id.nameUser);
        ImageView user_picture = (ImageView) headerView.findViewById(R.id.imgUser);

        try {
            response = new JSONObject(jsondata);
            Log.e("[NavigationActivity]", response.get("name").toString());
            user_name.setText(response.get("name").toString());

            if(!response.isNull("pictureUrlGoogle") && response.has("pictureUrlGoogle")){
                System.out.println("[NavigationActivity] url picture google ok");
                profile_pic_url = new JSONObject();
                profile_pic_url.put("url", response.getString("pictureUrlGoogle"));
            } else {
                profile_pic_data = new JSONObject(response.get("picture").toString());
                profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            }

            Picasso.with(this).load(profile_pic_url.getString("url"))
                    .into(user_picture);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cards) {
            this.showFragment(FRAGMENT_CARDSLIST);
        } else if (id == R.id.nav_decks) {
            //showFragment(new DecksFragment());
        } else if (id == R.id.nav_trade) {
           // showFragment(new TradeFragment());
        } else if (id == R.id.nav_quizz) {
            //showFragment(new QuizzFragment());
        } else if (id == R.id.nav_shop) {
            //showFragment(new ShopFragment());
        } else if (id == R.id.nav_deconnexion) {
            Intent intent = new Intent(NavigationActivity.this, ConnexionActivity.class);
            intent.putExtra("deconnexion", true);
            startActivity(intent);
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFragment(int fragmentIdentifier) {
        switch (fragmentIdentifier){
            case FRAGMENT_CARDSLIST :
                this.showCardsListFragment();
                break;
            default:
                break;
        }
    }

    private void showCardsListFragment(){
        if (this.cardsListFragment == null) this.cardsListFragment = CardsListFragment.newInstance();
        this.startTransactionFragment(this.cardsListFragment);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.drawer_layout, fragment).commit();
        }
    }


}
