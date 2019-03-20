package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.google.gson.JsonObject

interface InterfaceCallBackUser {
    fun onWorkUserDone(result: List<User>)
    fun onWorkAddDone(result: JsonObject)
}