package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(
        @SerializedName("id") @Expose var id: Int? = null,
        @SerializedName("pseudo") @Expose var pseudo: String? = null,
        @SerializedName("mail") @Expose var mail: String? = null,
        @SerializedName("coins") @Expose var coins: Int? = null,
        @SerializedName("firstName") @Expose var firstName: String? = null,
        @SerializedName("lastName") @Expose var lastName: String? = null,
        var cards: List<Card>? = null,
        var decks: List<Deck>? = null,
        var wishlist: List<Card>? = null,
        @SerializedName("facebookId") @Expose var facebookId: String? = null,
        @SerializedName("googleId") @Expose var googleId: String? = null,
        @SerializedName("password") @Expose var password: String? = null

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



