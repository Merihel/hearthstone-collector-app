package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(
        @SerializedName("id") @Expose var id: Int,
        @SerializedName("pseudo") @Expose var pseudo: String,
        @SerializedName("mail") @Expose var mail: String,
        @SerializedName("coins") @Expose var coins: Int,
        @SerializedName("firstName") @Expose var firstName: String? = null,
        @SerializedName("lastName") @Expose var lastName: String? = null,
        var cards: List<Card>? = null,
        var decks: List<Deck>? = null,
        var wishlist: List<Card>? = null,
        @SerializedName("facebookId") @Expose var facebookId: String? = null,
        @SerializedName("googleId") @Expose var googleId: String? = null
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



