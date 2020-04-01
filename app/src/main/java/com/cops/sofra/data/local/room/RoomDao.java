package com.cops.sofra.data.local.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cops.sofra.data.model.OrderItem;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    void add(OrderItem orderItem);

    @Update
    void onUpdate(OrderItem orderItem);

    @Delete
    void onDelete(OrderItem orderItem);

    @Query("SELECT * From OrderItem")
    LiveData<List<OrderItem>> getAll();
}
