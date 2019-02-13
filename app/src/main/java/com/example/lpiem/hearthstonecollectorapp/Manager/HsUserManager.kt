package com.example.lpiem.hearthstonecollectorapp.Manager

import com.example.lpiem.hearthstonecollectorapp.Models.User
import org.json.JSONObject

class HsUserManager {

    companion object {
        var loggedUser = User()
            public set
            public get

        var userSocialInfos = JSONObject()
            public set
            public get
    }

    private var instance: HsUserManager

    constructor() {
        instance = this
    }



}