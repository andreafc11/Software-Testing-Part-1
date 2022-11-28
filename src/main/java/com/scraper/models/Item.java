package com.scraper.models;

public class Item {
    // properties of items are coded here to facilitate mocking
    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;

    protected int priceInCents;

    // constructor injection
    public Item(int alertType, String heading, String description, String url, String imageUrl, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }
}
