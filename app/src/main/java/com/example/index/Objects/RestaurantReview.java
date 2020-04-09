package com.example.index.Objects;

public class RestaurantReview extends Review {
    private String userName;
    public RestaurantReview(String restaurantName, String restaurantUrl, String userId, String userName, float rating, String description) {
        super(restaurantName, restaurantUrl, userId, rating, description);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
