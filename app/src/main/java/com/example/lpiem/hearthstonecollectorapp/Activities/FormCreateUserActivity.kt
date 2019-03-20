package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager

import com.example.lpiem.hearthstonecollectorapp.Manager.APISingleton
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import com.example.lpiem.hearthstonecollectorapp.Util.HashUtil
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_connexion.*
import kotlinx.android.synthetic.main.activity_form_create_user.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.log

class FormCreateUserActivity : AppCompatActivity(), InterfaceCallBackUser {
    private val context = this
    private var user = User()
    private var hsUserManager = HsUserManager
    val controller = APIManager(this as InterfaceCallBackUser, null, null, null, null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_create_user)
        title = "Inscription"
        val actionBar = supportActionBar
        this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        btnCreate?.setOnClickListener {
            val valuePseudo = inpPseudo?.text.toString()
            val valueFirstName = inpFirstName?.text.toString()
            val valueLastName = inpLastName?.text.toString()
            val valueEmail = inpEmail?.text.toString()
            val valuePassword = inpPass?.text.toString()
            val valuePasswordConf = inpPassConfirm?.text.toString()

            Log.d("mlk", "pseudo : "+ valuePseudo)
            Log.d("mlk", "mail : "+ valueEmail)

            if (valuePseudo != null || valuePseudo !== "" && valueEmail != null && valueEmail !== "" && valuePassword != null || valuePassword != "" && valuePasswordConf != "" || valuePasswordConf != null) {
                if (valuePassword.equals(valuePasswordConf)) {
                    user = User(null, valuePseudo, valueEmail, null, valueFirstName, valueLastName, null, null, null, null, null, HashUtil.toMD5Hash(valuePassword))

                    println(user.toString())


                    controller.createUser(user)
                } else {
                    Toast.makeText(this@FormCreateUserActivity, "Les mots de passe de concordent pas", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@FormCreateUserActivity, "Les champs requis ne sont pas valides", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onWorkUserDone(result: List<User>) {
        hsUserManager.loggedUser = result.get(0)
        var intent = Intent(this@FormCreateUserActivity, NavigationActivity::class.java)
        startActivity(intent)
    }

    override fun onWorkAddDone(result: JsonObject) {
        Toast.makeText(this, result.get("message").asString, Toast.LENGTH_LONG).show()
        connectNewUser()
    }

    private fun connectNewUser() {
        controller.getUserByMail(user.mail!!);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                overridePendingTransition(0, 0)
                return true
            }
            R.id.btnCreate -> {
                val myIntent = Intent(this@FormCreateUserActivity, ConnexionActivity::class.java)
                startActivityForResult(myIntent, 0)
                return true
            }
        }
        return super.onOptionsItemSelected(item)



    }

}
