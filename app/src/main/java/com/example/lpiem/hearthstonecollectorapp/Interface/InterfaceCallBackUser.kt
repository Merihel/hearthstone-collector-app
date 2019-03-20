package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.google.gson.JsonObject

interface InterfaceCallBackUser {
    public fun onWorkUserDone(result: List<User>)
    public fun onWorkAddDone(result: JsonObject)
}