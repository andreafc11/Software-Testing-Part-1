package com.scraper.models;

public class Item {
    // properties of items are coded here to facilitate mocking
    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String image;

    protected int priceInCents;

    // constructor injection
    public Item(int alertType, String heading, String description, String url, String image, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.image = image;
        this.priceInCents = priceInCents;
    }

    // item constructor
    public Item() {
    }
    // getters and setters of item properties
    public int getAlertType() {
        return alertType;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public int getPriceInCents() {
        return priceInCents;
    }
    public void setAlertType(int alertType) {
        this.alertType = alertType;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }
}
