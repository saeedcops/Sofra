package com.cops.sofra.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class OrderItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String name,price,imageUrl,note,restaurantId;
    private int count,itemId;



    public OrderItem() {
    }
    @Ignore
    public OrderItem(String name, String imageUrl, String price, int count, int itemId,String restaurantId) {

        this.name = name;
        this.price = price;
        this.count=count;
        this.imageUrl=imageUrl;
        this.itemId=itemId;
        this.restaurantId=restaurantId;
    }
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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
