package com.example.lpiem.hearthstonecollectorapp.Interface

import com.google.gson.JsonObject

interface InterfaceCallBackTrade {
    fun onWorkTradesDone(result: JsonObject)
    fun onWorkTradeAdded(result: JsonObject)
}