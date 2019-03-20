package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.google.gson.JsonObject

interface InterfaceCallBackFriendship {
    fun onFriendshipDone(result: List<Friendship>)
    fun onPendingFriendshipDone(result: List<Friendship>)
    fun onDeleteDone(result: JsonObject)
    fun onAddDone(result: JsonObject)
    fun onAcceptDone(result: JsonObject)
}