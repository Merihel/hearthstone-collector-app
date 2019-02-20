package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Deck(id: Int, name: String, description: String) {
    @SerializedName("id") @Expose var id: Int = 0
    @SerializedName("name") @Expose var name: String? = null
    @SerializedName("description") @Expose var description: String? = null
    @SerializedName("cards_list") @Expose var cardsList: Array<Card>? = null

    override fun toString():String {
        return "Deck{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                '}'
    }

}
