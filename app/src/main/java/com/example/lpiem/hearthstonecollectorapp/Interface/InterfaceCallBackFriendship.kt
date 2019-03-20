package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Friendship
import com.google.gson.JsonObject

interface InterfaceCallBackFriendship {
    public fun onFriendshipDone(result: List<Friendship>)
    public fun onPendingFriendshipDone(result: List<Friendship>)
    public fun onDeleteDone(result: JsonObject)
    public fun onAddDone(result: JsonObject)
    public fun onAcceptDone(result: JsonObject)
}