package com.example.index.Objects;

public class Review {
    private String restaurantName, restaurantUrl;
    private String userId;
    private float rating;
    private String description;

    public Review(String restaurantName, String restaurantUrl, String userId, float rating, String description) {
        this.restaurantName = restaurantName;
        this.restaurantUrl = restaurantUrl;
        this.userId = userId;
        this.rating = rating;
        this.description = description;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantUrl() {
        return restaurantUrl;
    }

    public void setRestaurantUrl(String restaurantUrl) {
        this.restaurantUrl = restaurantUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
