package com.example.lpiem.hearthstonecollectorapp.Models

class Deck(id: Int, name: String, description: String) {
    var id: Int = 0
    var name: String? = null
    var description: String? = null
    var cardsList: Array<Card>? = null

    override fun toString():String {
        return "Deck{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                '}'
    }

}
