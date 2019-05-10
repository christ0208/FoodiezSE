package com.example.index.Objects;

import java.util.ArrayList;

public class Voucher {
    private String end_date;
    private int min_transaction;
    private float discount;
    private String terms_conditions;
    private String url;
    private ArrayList<String> restaurant_name;

    public Voucher() {
    }

    public Voucher(String end_date, int min_transaction, float discount, String terms_conditions, String url, ArrayList<String> restaurant_name) {
        this.end_date = end_date;
        this.min_transaction = min_transaction;
        this.discount = discount;
        this.terms_conditions = terms_conditions;
        this.url = url;
        this.restaurant_name = restaurant_name;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getMin_transaction() {
        return min_transaction;
    }

    public void setMin_transaction(int min_transaction) {
        this.min_transaction = min_transaction;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(String terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(ArrayList<String> restaurant_name) {
        this.restaurant_name = restaurant_name;
    }
}
