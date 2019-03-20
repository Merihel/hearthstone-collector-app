package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Card

interface InterfaceCallBackCard {
    public fun onWorkCardDone(result: List<Card>)
    public fun onWorkCardsDone(result: List<Card>)

}