package com.example.lpiem.hearthstonecollectorapp.Activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.lpiem.hearthstonecollectorapp.Adapter.FriendsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Fragments.PseudoDialog
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.example.lpiem.hearthstonecollectorapp.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_friend_list.*
import kotlinx.android.synthetic.main.toolbar_friend_list.view.*

class FriendListActivity : AppCompatActivity() {

    private var hsUserManager = HsUserManager
    private val controller = APIManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)

        controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
            onFriendshipDone(it)
        })

        friends_toolbar.btn_add_friend.setOnClickListener {
            askFriendIdentity()
        }

        friends_toolbar.friends_btn_back.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

    }

    private fun askFriendIdentity() {
        val dialog = PseudoDialog.newInstance(text = "", hint = "Pseudo", isMultiline = false)
        dialog.onOk = {
            val text = dialog.editText.text
            val jsonFriendshipAdd = JsonObject()
            jsonFriendshipAdd.addProperty("user1", hsUserManager.loggedUser.id!!)
            jsonFriendshipAdd.addProperty("user2", dialog.editText.text.toString())
            jsonFriendshipAdd.addProperty("isAccepted", false)
            jsonFriendshipAdd.addProperty("whoDemanding", hsUserManager.loggedUser.id!!)

            controller.addFriendship(jsonFriendshipAdd).observe(this, Observer {
                Toast.makeText(applicationContext, it.get("message").asString, Toast.LENGTH_LONG).show()
                controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
                    onFriendshipDone(it)
                })
            })

            Log.d("ASK PSEUDO", text.toString())
        }
        dialog.onCancel = {dialog.dismiss()}
        dialog.show(supportFragmentManager, "editDescription")
    }

    fun onFriendshipDone(result: List<Friendship>) {
        if (result.isNotEmpty()) {
            Log.d("onFriendshipDone", result[0].id.toString())
        }
        addDataToFriendList(result)
    }

    fun openDeleteDialog(friendship: Friendship) {
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title
        builder.setTitle("Supprimer " + friendship.user2.pseudo)

        // Display a message on alert dialog
        builder.setMessage("Êtes-vous sûr de vouloir supprimer '"+ friendship.user2.pseudo +"' ?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Oui"){ _, _ ->
            // Do something when user press the positive button
            controller.deleteFriendship(friendship.id).observe(this, Observer {
                Log.d("onDeleteDone", it.get("exit_code").asString)
                controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
                    onFriendshipDone(it)
                })

                Toast.makeText(applicationContext, it.get("message").asString, Toast.LENGTH_LONG).show()
            })
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("Non"){ _, _ ->
            Toast.makeText(applicationContext,"Opération annulée",Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun addDataToFriendList(res: List<Friendship>) {
        val listenerFriendList = object : FriendsListAdapter.Listener {
            override fun onItemClicked(item: Friendship) {
                Log.d("onItemClicked", "Clicked on "+item.user2.pseudo+" !")
            }
            override fun onDeleteClicked(item: Friendship) {
                Log.d("onDeleteClicked", "Deleting "+item.user2.pseudo+" ?")
                openDeleteDialog(item)
            }
            override fun onAcceptClicked(item: Friendship) {
                controller.acceptFriendship(item.id).observe(this@FriendListActivity, Observer {
                    Toast.makeText(applicationContext, it.get("message").asString, Toast.LENGTH_LONG).show()
                    controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this@FriendListActivity, Observer {
                        onFriendshipDone(it)
                    })
                })
            }
        }

        for(item in res) {
            Log.d("addDataToFriendlistItem", "Item id: "+item.id + ", isAccepted: "+item.isAccepted.toString())
        }

        rv_friends_list.adapter = FriendsListAdapter(res, this.applicationContext, listenerFriendList)
        rv_friends_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.applicationContext)
    }
}
