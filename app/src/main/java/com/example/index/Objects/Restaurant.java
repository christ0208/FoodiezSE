package com.example.index.Objects;

public class Restaurant {
    private int id;
    private String name, url, address, eateryType, openingDay, openingHours;
    private Float latitude, longitude;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Restaurant(int id, String name, String url, String address, String eateryType, String openingDay, String openingHours, Float latitude, Float longitude) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.address = address;
        this.eateryType = eateryType;
        this.openingDay = openingDay;
        this.openingHours = openingHours;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEateryType() {
        return eateryType;
    }

    public void setEateryType(String eateryType) {
        this.eateryType = eateryType;
    }

    public String getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(String openingDay) {
        this.openingDay = openingDay;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
