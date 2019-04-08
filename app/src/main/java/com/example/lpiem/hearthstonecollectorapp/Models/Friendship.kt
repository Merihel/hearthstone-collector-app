package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Friendship(
        @SerializedName("id") @Expose var id: Int,
        @SerializedName("user1") @Expose var user1: User,
        @SerializedName("user2") @Expose var user2: User,
        @SerializedName("isAccepted") @Expose var isAccepted: Boolean,
        @SerializedName("whoDemanding") @Expose var whoDemanding: Int




) {
    override fun toString():String {
        return user2.pseudo!!
    }
}