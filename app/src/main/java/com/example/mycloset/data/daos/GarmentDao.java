package com.example.mycloset.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycloset.data.entities.Garment;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface GarmentDao {
    @Insert
    List<Long> insertAll(Garment... garments);

    @Delete
    void delete(Garment garment);

    @Query("SELECT * FROM Garment")
    ListenableFuture<List<Garment>> selectAll();

    @Query("SELECT * FROM Garment")
    ListenableFuture<List<Garment>> selectAllEvenInactive();

    @Query("SELECT * FROM Garment WHERE garmentId=:id")
    ListenableFuture<Garment> selectById(long id);

    @Query("SELECT * FROM Garment WHERE UPPER(category)=:category")
    ListenableFuture<List<Garment>> selectCategory(String category);
}
