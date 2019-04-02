package com.example.lpiem.hearthstonecollectorapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Trade(
        @SerializedName("id") @Expose var id: Int? = null,
            @SerializedName("userAsker") @Expose var userAsker: User? = null,
            @SerializedName("userAsked") @Expose var userAsked: User? = null,
            @SerializedName("status") @Expose var status: String? = null,
            @SerializedName("cardAsker") @Expose var cardAsker: Card? = null,
            @SerializedName("cardAsked") @Expose var cardAsked: Card? = null,
            @SerializedName("isAskerOk") @Expose var isAskerOk: Boolean? = null,
            @SerializedName("isAskedOk") @Expose var isAskedOk: Boolean? = null )
