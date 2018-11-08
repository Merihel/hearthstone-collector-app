package com.example.lpiem.hearthstonecollectorapp.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lpiem.hearthstonecollectorapp.Manager.APIInterface;
import com.example.lpiem.hearthstonecollectorapp.Manager.APISingleton;
import com.example.lpiem.hearthstonecollectorapp.Models.User;
import com.example.lpiem.hearthstonecollectorapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    User user = new User(valuePseudo, valueFirstName, valueLastName, valueEmail, 0,  "", "");

                    System.out.println(user.toString());

                    APIInterface hearthstoneInstance = APISingleton.getInstance();
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
}
