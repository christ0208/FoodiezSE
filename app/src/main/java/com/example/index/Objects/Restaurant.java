package com.example.index.Objects;

public class Restaurant {
    private int id;
    private String name, url, address;

    public Restaurant(int id, String name, String url, String address) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.address = address;
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
}
