package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Trade
import com.google.gson.JsonObject

interface InterfaceCallBackTrade {
    fun onWorkTradesDone(result: List<Trade>)
    fun onWorkTradeUpdated(result: JsonObject)
    fun onWorkTradeAdded(result: JsonObject)
}