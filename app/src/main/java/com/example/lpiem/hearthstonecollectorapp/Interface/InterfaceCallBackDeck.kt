package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Deck
import com.google.gson.JsonObject

interface InterfaceCallBackDeck {
    public fun onWorkDecksDone(result: MutableList<Deck>)
    public fun onWorkDeckDone(result: List<Deck>)
    public fun onWorkDeleteDeckDone(result: JsonObject)
    public fun onWorkDeckAddedDone(result: JsonObject)


}