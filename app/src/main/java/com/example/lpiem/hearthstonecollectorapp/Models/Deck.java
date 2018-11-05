package com.example.lpiem.hearthstonecollectorapp.Models;

public class Deck {
    private int id;
    private String name;
    private String description;

    public Deck() {
    }

    public Deck(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // GETTERS //

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    // SETTERS //

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
