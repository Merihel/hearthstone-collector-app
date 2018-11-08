package com.example.lpiem.hearthstonecollectorapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("pseudo")
    @Expose
    private String pseudo;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("coins")
    @Expose
    private int coins;
    private List<Card> cards = null;
    private List<Deck> decks = null;
    private List<Card> wishlist = null;
    @SerializedName("facebookId")
    @Expose
    private String facebookId;
    @SerializedName("googleId")
    @Expose
    private String googleId;

    public User(int id, String pseudo, String firstName, String lastName, String mail, int coins, List<Card> cards, List<Card> wishlist, String facebookId, String googleId) {
        this.id = id;
        this.pseudo = pseudo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.coins = coins;
        this.cards = cards;
        this.wishlist = wishlist;
        this.facebookId = facebookId;
        this.googleId = googleId;
    }

    public User(String pseudo, String firstName, String lastName, String mail, int coins, String facebookId, String googleId) {
        this.pseudo = pseudo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.coins = coins;
        this.facebookId = facebookId;
        this.googleId = googleId;
    }

    public User() {
    }

    // GETTERS //

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public int getCoins() {
        return coins;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getWishlist() {
        return wishlist;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }



    // SETTERS //

    public void setId(int id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setWishlist(List<Card> wishlist) {
        this.wishlist = wishlist;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", coins='" + coins + '\'' +
                ", cards='" + cards + '\'' +
                ", wishlist='" + wishlist + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", googleId='" + googleId + '\'' +
                '}';
    }
}
