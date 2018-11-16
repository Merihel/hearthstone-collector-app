package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.lpiem.hearthstonecollectorapp.Manager.APISingleton
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import kotlinx.android.synthetic.main.activity_form_create_user.*

class FormCreateUserActivity : AppCompatActivity() {

    private var inpPseudo: EditText? = null
    private var inpFirstName: EditText? = null
    private var inpLastName: EditText? = null
    private var inpEmail: EditText? = null
    private var btnCreate: Button? = null
    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_create_user)
        title = "Inscription"
        val actionBar = supportActionBar

        //Récupération des fields
        inpPseudo = findViewById(R.id.inpPseudo)
        inpFirstName = findViewById(R.id.inpFirstName)
        inpLastName = findViewById(R.id.inpLastName)
        inpEmail = findViewById(R.id.inpEmail)
        btnCreate = findViewById(R.id.btnCreate)

        btnCreate?.setOnClickListener(View.OnClickListener {
            val valuePseudo = inpPseudo?.getText().toString()
            val valueFirstName = inpFirstName?.getText().toString()
            val valueLastName = inpLastName?.getText().toString()
            val valueEmail = inpEmail?.getText().toString()

            if (valuePseudo != null || valuePseudo !== "" && valueEmail != null && valueEmail !== "") {
                val user = User(8, "test", "test1@fzef.fr", 44, null, null, null, null, null, null, null)

                println(user.toString())

                val hearthstoneInstance = APISingleton.hearthstoneInstance
                val call = hearthstoneInstance!!.createUser(user)

                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val `object` = response.body() //On get l'objet à l'index 0
                            println(`object`!!.toString())
                        } else {
                            Log.d("[CreateUserActivity]", "error on response : " + response.message())
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        println("[APIManager]createUser Erreur callback ! $t")
                    }
                })
            } else {
                Toast.makeText(this@FormCreateUserActivity, "Pseudo et Email ne peuvent pas être vides", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onWorkDone(result: Boolean?) {
        if (result!!)
            //adapter.notifyDataSetChanged()
        else
            Toast.makeText(this@FormCreateUserActivity, "probleme de retour de l'API", Toast.LENGTH_SHORT).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myIntent = Intent(this@FormCreateUserActivity, ConnexionActivity::class.java)
        startActivityForResult(myIntent, 0)
        return true
    }

}


/*


public class FormCreateUserActivity extends AppCompatActivity {

    private EditText inpPseudo;
    private EditText inpFirstName;
    private EditText inpLastName;
    private EditText inpEmail;
    private Button btnCreate;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_create_user);
        setTitle("Inscription");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        //Récupération des fields
        inpPseudo = findViewById(R.id.inpPseudo);
        inpFirstName = findViewById(R.id.inpFirstName);
        inpLastName = findViewById(R.id.inpLastName);
        inpEmail = findViewById(R.id.inpEmail);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valuePseudo = inpPseudo.getText().toString();
                String valueFirstName = inpFirstName.getText().toString();
                String valueLastName = inpLastName.getText().toString();
                String valueEmail = inpEmail.getText().toString();

                if (valuePseudo != null || valuePseudo != "" && valueEmail != null && valueEmail != "") {
                    User user = new User(8, "test", "test1@fzef.fr", 44, null, null, null, null, null, null, null);

                    System.out.println(user.toString());

                    APIInterface hearthstoneInstance = APISingleton.INSTANCE.getHearthstoneInstance();
                    Call<User> call = hearthstoneInstance.createUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                Object object = response.body(); //On get l'objet à l'index 0
                                System.out.println(object.toString());
                            } else {
                                Log.d("[CreateUserActivity]", "error on response : " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            System.out.println("[APIManager]createUser Erreur callback ! "+ t);
                        }
                    });
                } else {
                    Toast.makeText(context, "Pseudo et Email ne peuvent pas être vides", Toast.LENGTH_SHORT);
                }

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(FormCreateUserActivity.this, ConnexionActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}



 */
