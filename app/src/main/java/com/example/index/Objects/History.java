package com.example.index.Objects;

public class History {
    private int id;
    private String restaurant_name, restaurant_url;
    private float rating;
    private String created_at;

    public History(int id, String restaurant_name, String restaurant_url, float rating, String created_at) {
        this.id = id;
        this.restaurant_name = restaurant_name;
        this.restaurant_url = restaurant_url;
        this.rating = rating;
        this.created_at = created_at;
    }

    public String getRestaurant_url() {
        return restaurant_url;
    }

    public void setRestaurant_url(String restaurant_url) {
        this.restaurant_url = restaurant_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
