package com.cops.sofra.utils;

public class SelectedOrder {
    private String name,price,imageUrl,note;
    private int count,itemId;


    public SelectedOrder(String name,String imageUrl, String price,int count,int itemId) {

        this.name = name;
        this.price = price;
        this.count=count;
        this.imageUrl=imageUrl;
        this.itemId=itemId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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


}
