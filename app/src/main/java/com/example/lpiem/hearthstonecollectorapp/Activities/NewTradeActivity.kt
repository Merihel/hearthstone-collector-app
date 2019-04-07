package com.example.lpiem.hearthstonecollectorapp.Activities

import androidx.appcompat.app.AppCompatActivity
import com.example.lpiem.hearthstonecollectorapp.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class NewTradeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var listFriends = arrayOf("ami1", "ami2", "ami3")
    var spinnerFriends: Spinner? = null
    var spinnerCards: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_trade)

        spinnerFriends = findViewById(R.id.new_trade_spinner_friends)
        spinnerCards = findViewById(R.id.new_trade_spinner_cards)
        spinnerFriends!!.onItemSelectedListener = this

        //ArrayAdapter
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listFriends)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerFriends!!.adapter = aa
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

        if (arg1.id == android.R.id.text1) {
            if (position != 0) {
                //TODO get cards by pseudo
                var cardsAmi1 = arrayOf("car150", "card114", "card778")
                var cardsAmi2 = arrayOf("car12", "card26", "card46")
                var cardsAmi3 = arrayOf("car22", "card9", "card115")

                if (position == 1) {
                    spinnerCards!!.onItemSelectedListener = this
                    //ArrayAdapter
                    val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, cardsAmi1)
                    // Set layout to use when the list of choices appear
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Set Adapter to Spinner
                    spinnerCards!!.adapter = aa
                }
            }
        } else {
            Log.d("SpinnerCardsSelect", position.toString())
        }

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}
