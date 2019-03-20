package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.google.gson.JsonObject

interface InterfaceCallBackDeck {
    fun onWorkDecksDone(result: List<Deck>)
    fun onWorkDeckDone(result: List<Deck>)
    fun onWorkDeleteDeckDone(result: JsonObject)
    fun onWorkDeckAddedDone(result: JsonObject)
    fun onWorkDeckUpdatedDone(result: JsonObject)


}