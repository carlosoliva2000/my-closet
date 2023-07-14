package com.example.mycloset.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.entities.Garment;

@Database(entities = {Garment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public static AppDatabase get(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class, "DB-MyCloset").build();
        return instance;
    }
    public abstract GarmentDao garmentDao();
}
