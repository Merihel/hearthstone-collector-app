package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import com.example.lpiem.hearthstonecollectorapp.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.view.View
import com.example.lpiem.hearthstonecollectorapp.Fragments.CardsListFragment
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackCard
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Models.Card
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_connexion.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class ConnexionActivity : InterfaceCallBackUser, InterfaceCallBackCard, AppCompatActivity() {

    //Variables Facebook
    private var callbackManager: CallbackManager? = null
    private var fbLoginManager: com.facebook.login.LoginManager? = null

    //Variables Google
    private var gClient: GoogleSignInClient? = null
    private var gAccount: GoogleSignInAccount? = null

    //TEST DEBUG
    private var cardsListFragment: androidx.fragment.app.Fragment? = null

    //Variables autres
    private var isLoggedIn: Boolean? = null
    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        ////////        TEST         /////////
        val controller = APIManager(this as InterfaceCallBackUser, this as InterfaceCallBackCard)
        controller.getCardById(1)
        controller.getUserById(15)

        gAccount = GoogleSignIn.getLastSignedInAccount(this)

        var bundle = intent.extras
        if (bundle.getBoolean("deconnexion")) {
            Log.d("onCreate","Deconnexion...")
            LoginManager.getInstance().logOut()

            //DECONNEXION GOOGLE
            var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build()
            gClient = GoogleSignIn.getClient(this, gso)
            googleSignOut()
        }

        //TESTS ---- BOUTON D'ACCES RAPIDE SUR L'IMAGE
        imgLogo.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(this@ConnexionActivity, NavigationActivity::class.java)
                startActivity(intent)
            }
        })


        //FACEBOOK
        fbLoginManager = com.facebook.login.LoginManager.getInstance()
        callbackManager = CallbackManager.Factory.create()
        //Callbacks registration
        fbLoginManager?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> { // facebookSignInButton
            override fun onSuccess(loginResult: LoginResult) {
                getUserFacebookDetails(loginResult.accessToken)
            }
            override fun onCancel() {}
            override fun onError(exception: FacebookException) {
                Log.e("facebookexception", exception.message)
            }
        })

        loginBtnFb.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                fbLoginManager?.logInWithReadPermissions(this@ConnexionActivity, Arrays.asList("email", "public_profile", "user_birthday"))
            }
        })

        //GOOGLE
        googleConnexion(gAccount)

        //Click listener : Google SignIn Button
        google_sign_in.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                googleSignIn()
            }
        })
        //Click listener : Google SignOut Button
        google_sign_out.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                googleSignOut()
            }
        })

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build()
        gClient = GoogleSignIn.getClient(this, gso)

        //COMPTE HORS RESEAUX SOCIAUX
        btnCreationCompte.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                //OUVRE EN EFFET LE FORMULAIRE DE CREATION
                //var intent = Intent(this@ConnexionActivity, FormCreateUserActivity::class.java)
                //startActivity(intent)

                //OUVRE LE FRAGMENT DES CARTES POUR TESTER
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.root_layout_test, CardsListFragment.newInstance(), "cardsListFragment")
                        .commit()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        var accessToken = AccessToken.getCurrentAccessToken()
        isLoggedIn = accessToken != null && !accessToken.isExpired
        if (isLoggedIn!!) {
            getUserFacebookDetails(accessToken)
        }
        gAccount = GoogleSignIn.getLastSignedInAccount(this@ConnexionActivity)
        googleConnexion(gAccount)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //for facebook
        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }


    /*
    FACEBOOK FUNCTIONS
     */
    //Get user details to send it to NavigationActivity
    protected fun getUserFacebookDetails(loginResult: AccessToken) {
        var dataRequest = GraphRequest.newMeRequest(loginResult,
            object: GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(jsonObject: JSONObject?, response: GraphResponse?) {
                    Log.d("Connexion", jsonObject.toString())
                    var intent = Intent(this@ConnexionActivity, NavigationActivity::class.java)
                    try {
                        Log.d("Facebook Mail", jsonObject!!.getString("email"))
                    } catch(e: JSONException) {
                        Log.d("Facebook Mail Error", e.message)
                    }
                    startActivity(intent)
                }
            })
        var permissionParam = Bundle()
        permissionParam.putString("fields", "id,name,email,picture.width(120).height(120)")
        dataRequest.parameters = permissionParam
        dataRequest.executeAsync()
    }



    /*
    GOOGLE FUNCTIONS
     */

    //Sign-in function
    fun googleSignIn() {
        var signInIntent = gClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //Sign out functions with OnCompleteListener
    fun googleSignOut() {
        gClient!!.signOut()
            .addOnCompleteListener(this, object : OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    Log.d("googleSignOut", "Successfully disconnected user")
                    googleConnexion(null)
                }
            })
    }

    fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            var account = task.getResult(ApiException::class.java)
            googleConnexion(account)
        } catch (e: ApiException) {
            Log.e("GoogleApiError", "signInResult:failed code=" + e.statusCode)
            googleConnexion(null)
        }
    }

    //Connection handler
    fun googleConnexion(signInAccount: GoogleSignInAccount?) {
        if (signInAccount == null) {
            Log.d("googleConnexion", "User non connecté...")
            google_sign_in.visibility = View.VISIBLE
            google_sign_out.visibility = View.GONE
        } else {
            var intent = Intent(this, NavigationActivity::class.java)
            var jsonObject = JSONObject()
            try {
                jsonObject.put("name", signInAccount.displayName)
                jsonObject.put("email", signInAccount.email)
                jsonObject.put("picture", signInAccount.photoUrl)
                Log.d("Intent extras", jsonObject.toString())
            } catch (e: JSONException) {
                Log.e("JSONException", e.message)
            }
            intent.putExtra("userProfile", jsonObject.toString())
            startActivity(intent)

            Log.d("User Connected", signInAccount.displayName + " ; " + signInAccount.email)

            google_sign_in.visibility = View.GONE
            google_sign_out.visibility = View.VISIBLE
        }
    }


    override fun onWorkUserDone(result: List<User>) {
        if (result != null) {
            Log.i("OnWorkDone", "OK")
            System.out.println("MY USER > " + result.get(0).pseudo)
        } else {
            Log.e("OnWorkDone Error", "Not ok")
        }
    }

    override fun onWorkCardDone(result: List<Card>) {
        if (result != null) {
            Log.d("OnWorkCardDone", result.get(0).flavor)
        }
    }

    override fun onWorkCardsDone(result: List<Card>) {
        if (result != null) {
            Log.d("OnWorkCardDone", result.get(0).flavor)
        }
    }
}
