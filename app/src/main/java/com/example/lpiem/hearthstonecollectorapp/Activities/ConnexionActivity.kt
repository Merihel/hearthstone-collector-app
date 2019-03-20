package com.example.lpiem.hearthstonecollectorapp.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lpiem.hearthstonecollectorapp.Fragments.PseudoDialog
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackLogin
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackSync
import com.example.lpiem.hearthstonecollectorapp.Interface.InterfaceCallBackUser
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.example.lpiem.hearthstonecollectorapp.R
import com.example.lpiem.hearthstonecollectorapp.Util.HashUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_connexion.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class ConnexionActivity : InterfaceCallBackSync, InterfaceCallBackLogin, InterfaceCallBackUser, AppCompatActivity() {

    //Variables Facebook
    private var callbackManager: CallbackManager? = null
    private var fbLoginManager: com.facebook.login.LoginManager? = null

    //Variables Google
    private var gClient: GoogleSignInClient? = null
    private var gAccount: GoogleSignInAccount? = null

    //Variables autres
    private var isLoggedIn: Boolean? = null
    private var socialState = ""
    private val RC_SIGN_IN = 100
    private var apiManager = APIManager()
    private var userExtras = JSONObject()
    private var hsUserManager = HsUserManager


    companion object {

        fun newIntent(context: Context, isDeconnect: Boolean): Intent {
            val intent = Intent(context, ConnexionActivity::class.java)
            intent.putExtra("deconnexion", isDeconnect)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)
        gAccount = GoogleSignIn.getLastSignedInAccount(this)

        var bundle = intent.extras

        /*
        *
        *  GOOGLE/FACEBOOK : DECONNEXION
        *
        */
        var boolDeco = false
        try {boolDeco = bundle.getBoolean("deconnexion")} catch(ex: NullPointerException) {Log.e("ERROR", "Error with deconnexion")}
        if (boolDeco) {
            Log.d("onCreate","Deconnexion...")
            //DECONNEXION FACEBOOK
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

        /*
        * CLASSIQUE : CONNEXION
        */
        loginBtnClassic.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                Log.d("Login Mail", inpLoginMail.text.toString())
                Log.d("Login Pass", inpLoginPassword.text.toString())
                Log.d("Login Pass MD5", HashUtil.toMD5Hash(inpLoginPassword.text.toString()))

                var json = JsonObject()
                json.addProperty("identifier", inpLoginMail.text.toString())
                json.addProperty("password", HashUtil.toMD5Hash(inpLoginPassword.text.toString()))
                apiManager.checkLogin(json, this@ConnexionActivity)
            }
        })

        /*
        *
        *  FACEBOOK : CONNEXION
        *
        */
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

        /*
        *
        *  GOOGLE : CONNEXION
        *
        */
        googleConnexion(gAccount)

        //Click listener : Google SignIn Button
        google_sign_in.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                googleSignIn()
            }
        })

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build()
        gClient = GoogleSignIn.getClient(this, gso)


        /*
        *
        *  COMPTE CLASSIQUE: BTN DE CREATION DE COMPTE
        *
        */
        btnCreationCompte.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                //OUVRE EN EFFET LE FORMULAIRE DE CREATION
                var intent = Intent(this@ConnexionActivity, FormCreateUserActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        // CONNEXION AUTO FB ET GOOGLE
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
                override fun onCompleted(jsonObject: JSONObject, response: GraphResponse?) {
                    Log.d("Connexion", jsonObject.toString())
                    socialState = "f"
                    jsonObject.put("type", "f")
                    hsUserManager.userSocialInfos = jsonObject
                    userSyncCheckStep1(jsonObject.getString("email"))
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
        } else {
            System.out.println(signInAccount.email)
            try {
                var json = JSONObject()
                json.put("id", signInAccount.id)
                json.put("name", signInAccount.displayName)
                json.put("email", signInAccount.email)
                json.put("picture", signInAccount.photoUrl)
                json.put("type", "g")
                hsUserManager.userSocialInfos = json
            } catch (e: JSONException) {
                Log.e("JSONException", e.message)
            }

            Log.d("G User Connected", signInAccount.displayName + " ; " + signInAccount.email)
            socialState = "g"
            userSyncCheckStep1(signInAccount.email)
        }
    }

    fun userSyncCheckStep1(mail: String?) {
        var json = JsonObject()
        json.addProperty("mail", mail)
        apiManager.getUserByMail(mail!!,this)
        apiManager.syncUserStep1(json,this)
    }

    fun userSyncCheckStep2(pseudo: String, mail: String, id: String, type: String) {
        var json = JsonObject()
        json.addProperty("pseudo", pseudo)
        json.addProperty("mail", mail)
        when(socialState) {
            "g" -> json.addProperty("googleId", id)
            "f" -> json.addProperty("facebookId", id)
        }

        apiManager.syncUserStep2(type, json,this)

    }


    override fun onWorkSyncDone(result: JsonObject) {
        Log.d("SocialAccount", "CHECKED SYNC WITH " + socialState)
        when (result.get("exit_code").asInt) {
            0 -> {
                Log.d("WorkSyncDone", "Can connect with: "+hsUserManager.userSocialInfos.get("email") as String)
                apiManager.getUserByMail(hsUserManager.userSocialInfos.get("email") as String,this)
            }
            2 -> {
                Log.d("WorkSyncDone", "Compte trouvé, pseudo nécessaire")
                askPseudo("update")
            }
            3 -> {
                Log.d("WorkSyncDone", "Compte introuvable, prêt à être créé, pseudo nécessaire")
                askPseudo("create")
            }
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }

    override fun onWorkSyncDone2(result: JsonObject) {
        when (result.get("exit_code").asInt) {
            0 -> apiManager.getUserByMail(hsUserManager.userSocialInfos.get("email") as String,this)
            1 -> Toast.makeText(this, "Une erreur est survenue lors de la création de compte", Toast.LENGTH_LONG)
        }
    }

    fun askPseudo(type: String) { //Le type est le type d'opération : create le compte ou update en fonction de l'état renvoyé par API
        val dialog = PseudoDialog.newInstance(text = "", hint = "Pseudo", isMultiline = false)
        dialog.onOk = {
            val text = dialog.editText.text
            Log.d("ASK PSEUDO", text.toString())
            when(socialState) {
                "g" -> {
                    hsUserManager.userSocialInfos.put("pseudo", text.toString())
                    System.out.println(hsUserManager.userSocialInfos.toString())
                    userSyncCheckStep2(text.toString(), hsUserManager.userSocialInfos.getString("email"), hsUserManager.userSocialInfos.getString("id"), type)
                }
                "f" -> {
                    hsUserManager.userSocialInfos.put("pseudo", text.toString())
                    System.out.println(hsUserManager.userSocialInfos.toString())
                    userSyncCheckStep2(text.toString(), hsUserManager.userSocialInfos.getString("email"), hsUserManager.userSocialInfos.getString("id"), type)
                }
            }
        }
        dialog.show(supportFragmentManager, "editDescription")
    }

    //Si tout est ok on continue la connexion avec les réseaux sociaux
    fun continueSocialConnection() {
        var intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }

    //Quand on récupère l'user associé aux comptes sociaux
    override fun onWorkUserDone(result: List<User>) {
        var user = result.get(0)
        hsUserManager.loggedUser = user
        continueSocialConnection()
    }

    //QUAND LA CONNEXION CLASSIQUE SE FAIT

    override fun onWorkLoginDone(result: User) {
        Log.d("Simple Connect With", result.toString())
        hsUserManager.loggedUser = result
        Toast.makeText(baseContext, "Bienvenue, "+result.pseudo+" !", Toast.LENGTH_LONG).show()
        var intent = Intent(this@ConnexionActivity, NavigationActivity::class.java)
        Log.d("onWorkLoginDone", "NO SOCIAL ACCOUNT")
        Log.d("onWorkLoginDone", "User Logged: "+hsUserManager.loggedUser.toString())

        startActivity(intent)
    }

    override fun onWorkLoginError(error: String) {
        Log.d("onWorkLoginError", error)
        Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onWorkAddDone(result: JsonObject) {}
}
