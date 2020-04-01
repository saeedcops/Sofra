package com.cops.sofra.viewModel.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cops.sofra.data.local.room.ItemRepository;
import com.cops.sofra.data.model.OrderItem;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;
    private LiveData<List<OrderItem>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository= new ItemRepository(application);
        allItems=itemRepository.getAllItems();
    }

    public void add(OrderItem orderItem){
        itemRepository.add(orderItem);
    }

    public void onDelete(OrderItem orderItem){
        itemRepository.onDelete(orderItem);
    }

    public void onUpdate(OrderItem orderItem){
        itemRepository.onUpdate(orderItem);
    }

    public LiveData<List<OrderItem>> getAllItems(){
        return allItems;
    }
}
