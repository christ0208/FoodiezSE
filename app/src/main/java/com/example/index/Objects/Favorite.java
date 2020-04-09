package com.example.index.Objects;

public class Favorite {
    private int id;
    private String restaurantName, restaurantUrl, restaurantAddress, userId;

    public Favorite(int id, String restaurantName, String restaurantUrl, String restaurantAddress, String userId) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantUrl = restaurantUrl;
        this.restaurantAddress = restaurantAddress;
        this.userId = userId;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
