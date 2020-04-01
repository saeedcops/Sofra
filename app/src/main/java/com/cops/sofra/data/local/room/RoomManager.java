package com.cops.sofra.data.local.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cops.sofra.data.model.OrderItem;

@Database(entities = {OrderItem.class},version = 1,exportSchema = false)
public abstract class RoomManager extends RoomDatabase {
    private static RoomManager roomManager;

    public abstract RoomDao roomDao();
    public static synchronized RoomManager getInstance(Context context){

        if(roomManager==null){
            roomManager= Room.databaseBuilder(context.getApplicationContext(),RoomManager.class,
                    "sofra_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return roomManager;
    }

    private static RoomDatabase.Callback callback= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(roomManager).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>{

        private RoomDao roomDao;

        PopulateAsyncTask(RoomManager roomManager) {
            this.roomDao = roomManager.roomDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            roomDao.add(new OrderItem("name","image","21",2,1));
            return null;
        }
    }
}
