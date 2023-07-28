package com.example.mycloset.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.mycloset.data.daos.EventDao;
import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.daos.OutfitDao;
import com.example.mycloset.data.entities.Event;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.data.entities.Outfit;
import com.example.mycloset.data.entities.OutfitGarmentCrossRef;

@Database(entities = {Garment.class, Outfit.class, OutfitGarmentCrossRef.class, Event.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public static AppDatabase get(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class, "DB-MyCloset").build();
        return instance;
    }
    public abstract GarmentDao garmentDao();
    public abstract OutfitDao outfitDao();
    public abstract EventDao eventDao();
}
