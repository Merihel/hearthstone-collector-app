package com.example.lpiem.hearthstonecollectorapp.Interface

import com.example.lpiem.hearthstonecollectorapp.Models.Card

interface InterfaceCallBackCard {
    fun onWorkCardDone(result: List<Card>)
    fun onWorkCardsDone(result: List<Card>)

}