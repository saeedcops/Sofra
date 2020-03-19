package com.cops.sofra.utils;

public class SelectedOrder {
    private String name,price,imageUrl;
    private int count;

    public SelectedOrder(String name,String imageUrl, String price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
