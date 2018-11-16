package com.example.lpiem.hearthstonecollectorapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpiem.hearthstonecollectorapp.Manager.APIInterface;
import com.example.lpiem.hearthstonecollectorapp.Manager.APISingleton;
import com.example.lpiem.hearthstonecollectorapp.Models.Card;
import com.example.lpiem.hearthstonecollectorapp.Models.User;
import com.example.lpiem.hearthstonecollectorapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardsListFragment extends Fragment {


    public CardsListFragment() {
    }


    public static CardsListFragment newInstance() {
        return (new CardsListFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cards_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*
         * TEST : récupérer le user avec l'id 1
         */
        final TextView txtTest = view.findViewById(R.id.txtTest);

        APIInterface hearthstoneInstance = APISingleton.INSTANCE.getHearthstoneInstance();
        Call<User> callUser = hearthstoneInstance.getUser(1);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body(); //On get l'objet à l'index 0

                    Log.d("[ConnexionActivity]", "User  : " + user.getCards().get(0).getId());
                    int cardId = user.getCards().get(0).getId();
                    System.out.println(cardId);
                    txtTest.setText("Cartes user : "+ cardId);
                } else {
                    Log.d("[ConnexionActivity]", "error on response : " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<User>call, Throwable t) {
                System.out.println("[APIManager]getUserByID Erreur callback ! "+ t);
            }
        });
    }
}
