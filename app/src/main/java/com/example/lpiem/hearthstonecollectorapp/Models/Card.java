package com.example.lpiem.hearthstonecollectorapp.Models;

public class Card {
    private int id;
    private String hsId;

    // DETAILS DE LA CARTE
    private int cost;
    private int attack;
    private boolean collectible;
    private int durability;
    private int health;
    private String set;
    private String classCard;
    private String quality;
    private String race;
    private String faction;
    private String type;
    private String text;
    private String flavor;
    private String img;
    private String imgGold;


    public Card(int id, String hsId, int cost, int attack, boolean collectible, int durability, int health, String set, String classCard, String quality, String race, String faction, String type, String text, String flavor, String img, String imgGold) {
        this.id = id;
        this.hsId = hsId;
        this.cost = cost;
        this.attack = attack;
        this.collectible = collectible;
        this.durability = durability;
        this.health = health;
        this.set = set;
        this.classCard = classCard;
        this.quality = quality;
        this.race = race;
        this.faction = faction;
        this.type = type;
        this.text = text;
        this.flavor = flavor;
        this.img = img;
        this.imgGold = imgGold;
    }

    public Card() {
    }

    // GETTERS //

    public int getId() {
        return id;
    }

    public String getHsId() {
        return hsId;
    }

    public int getCost() {
        return cost;
    }

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }

    public int getHealth() {
        return health;
    }

    public String getSet() {
        return set;
    }

    public String getClassCard() {
        return classCard;
    }

    public String getQuality() {
        return quality;
    }

    public String getRace() {
        return race;
    }

    public String getFaction() {
        return faction;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getFlavor() {
        return flavor;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public String getImg() {
        return img;
    }

    public String getImgGold() {
        return imgGold;
    }

    // SETTERS //
    public void setId(int id) {
        this.id = id;
    }

    public void setHsId(String hsId) {
        this.hsId = hsId;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public void setClassCard(String classCard) {
        this.classCard = classCard;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }
}
