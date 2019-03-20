package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.User

interface InterfaceCallBackLogin {
    fun onWorkLoginDone(result: User)
    fun onWorkLoginError(error: String)
}