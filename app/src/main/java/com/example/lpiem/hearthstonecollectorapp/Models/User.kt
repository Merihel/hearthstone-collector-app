package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(
        @SerializedName("id") @Expose var id: Int,
        @SerializedName("pseudo") @Expose var pseudo: String,
        @SerializedName("firstName") @Expose var firstName: String?,
        @SerializedName("lastName") @Expose var lastName: String?,
        @SerializedName("mail") @Expose var mail: String,
        @SerializedName("coins") @Expose var coins: Int,
        var cards: List<Card>?,
        var decks: List<Deck>?,
        var wishlist: List<Card>?,
        @SerializedName("facebookId") @Expose var facebookId: String?,
        @SerializedName("googleId") @Expose var googleId: String?
) {
    override fun toString():String {
        return "UserJava{" +
                "id='" + id +
                ", pseudo='" + pseudo +
                ", firstName='" + firstName +
                ", lastName='" + lastName +
                ", mail='" + mail +
                ", coins='" + coins +
                ", cards='" + cards +
                ", wishlist='" + wishlist +
                ", facebookId='" + facebookId +
                ", googleId='" + googleId +
                '}'
    }
}



