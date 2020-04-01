package com.cops.sofra.data.local.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cops.sofra.data.model.OrderItem;

import java.util.List;

public class ItemRepository {

    private RoomDao roomDao;
    private LiveData<List<OrderItem>> getAllItems;

//Constructor
    public ItemRepository(Application application) {
        RoomManager db=RoomManager.getInstance(application);
        roomDao=db.roomDao();
        getAllItems=roomDao.getAll();
    }

//add method
    public void add(OrderItem orderItem){

        new AddAsyncTask(roomDao).execute(orderItem);
    }

//update method
    public void onUpdate(OrderItem orderItem){

        new UpdateAsyncTask(roomDao).execute(orderItem);
    }

//delete method
    public void onDelete(OrderItem orderItem){

        new DeleteAsyncTask(roomDao).execute(orderItem);
    }

//get all method
    public LiveData<List<OrderItem>> getAllItems(){
        return getAllItems;
    }

//add AsyncTask
    private static class AddAsyncTask extends AsyncTask<OrderItem,Void,Void>{

        private RoomDao roomDao;

        public AddAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(OrderItem... orderItems) {
            roomDao.add(orderItems[0]);
            return null;
        }
    }

//update AsyncTask
    private static class UpdateAsyncTask extends AsyncTask<OrderItem,Void,Void>{

        private RoomDao roomDao;

        public UpdateAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(OrderItem... orderItems) {
            roomDao.onUpdate(orderItems[0]);
            return null;
        }
    }

//delete AsyncTask
    private static class DeleteAsyncTask extends AsyncTask<OrderItem,Void,Void>{

        private RoomDao roomDao;

        public DeleteAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(OrderItem... orderItems) {
            roomDao.onDelete(orderItems[0]);
            return null;
        }
    }
}
