package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import com.example.lpiem.hearthstonecollectorapp.Util.HashUtil
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_form_create_user.*

class FormCreateUserActivity : AppCompatActivity(), InterfaceCallBackUser {
    private val context = this
    private var user = User()
    private var hsUserManager = HsUserManager
    val controller = APIManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_create_user)
        title = "Inscription"
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


                    controller.createUser(user,this)
                } else {
                    Toast.makeText(this@FormCreateUserActivity, "Les mots de passe de concordent pas", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@FormCreateUserActivity, "Les champs requis ne sont pas valides", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onWorkUserDone(result: List<User>) {
        hsUserManager.loggedUser = result[0]
        val intent = Intent(this@FormCreateUserActivity, NavigationActivity::class.java)
        startActivity(intent)
    }

    override fun onWorkAddDone(result: JsonObject) {
        Toast.makeText(this, result.get("message").asString, Toast.LENGTH_LONG).show()
        connectNewUser()
    }

    private fun connectNewUser() {
        controller.getUserByMail(user.mail!!,this)
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
