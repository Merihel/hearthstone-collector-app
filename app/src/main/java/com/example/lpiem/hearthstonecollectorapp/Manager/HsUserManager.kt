package com.example.lpiem.hearthstonecollectorapp.Manager

import com.example.lpiem.hearthstonecollectorapp.Models.User
import org.json.JSONObject

class HsUserManager() {

    companion object {
        var loggedUser = User()
            set
            get

        var userSocialInfos = JSONObject()
            set
            get

        var defautThumbnail = "https://lh3.googleusercontent.com/a-/AAuE7mBcLqrbCXYjenRbaQURI7z4mLeUGdwf05n9UGxD_Q"
    }

    private var instance: HsUserManager

    init {
        instance = this
    }



}