package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.User
import com.google.gson.JsonObject

interface InterfaceCallBackLogin {
    public fun onWorkLoginDone(result: User)
    public fun onWorkLoginError(error: String)
}