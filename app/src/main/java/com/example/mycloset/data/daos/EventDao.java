package com.example.mycloset.data.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.google.common.util.concurrent.ListenableFuture;

import com.example.mycloset.data.entities.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    List<Long> insertAll(Event... events);

    @Delete
    void delete(Event event);

    @Query("SELECT * FROM Event")
    ListenableFuture<List<Event>> selectAll();
}
