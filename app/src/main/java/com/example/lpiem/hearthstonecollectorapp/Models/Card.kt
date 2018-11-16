package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Card(
        @SerializedName("cardId") @Expose var id: Int,
        @SerializedName("cardId") @Expose var hsId: String,
        @SerializedName("name") @Expose var name: String,
        @SerializedName("login") @Expose var cost: Int,
        @SerializedName("attack") @Expose var attack: Int,
        @SerializedName("collectible") @Expose var collectible: Boolean,
        @SerializedName("durability") @Expose var durability: Int,
        @SerializedName("health") @Expose var health: Int,
        @SerializedName("set") @Expose var set: String,
        @SerializedName("playerClass") @Expose var classCard: String,
        @SerializedName("quality") @Expose var quality: String,
        @SerializedName("race") @Expose var race: String,
        @SerializedName("faction") @Expose var faction: String,
        @SerializedName("rarity") @Expose var rarity: String,
        @SerializedName("type") @Expose var type: String,
        @SerializedName("text") @Expose var text: String,
        @SerializedName("flavor") @Expose var flavor: String,
        @SerializedName("img") @Expose var img: String,
        @SerializedName("imgGold") @Expose var imgGold: String ) {

    override fun toString(): String {
        return "Card { " +
                "id = " + id +
                "hsId = " + hsId +
                "}"
    }



}