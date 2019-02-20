package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Deck

interface InterfaceCallBackDeck {
    public fun onWorkDecksDone(result: MutableList<Deck>)
    public fun onWorkDeckDone(result: List<Deck>)


}