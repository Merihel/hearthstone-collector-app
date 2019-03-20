package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.User

interface InterfaceCallBackUser {
    public fun onWorkUserDone(result: List<User>)
}