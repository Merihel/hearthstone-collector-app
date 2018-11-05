package com.example.lpiem.hearthstonecollectorapp.Models;

public class Question {
    private int id;
    private String description;
    private String title;
    private int cost;


    public Question() {
    }

    public Question(int id, String description, String title, int cost) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.cost = cost;
    }

    // GETTERS //

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getCost() {
        return cost;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
