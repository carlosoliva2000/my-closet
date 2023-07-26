package com.example.mycloset.data.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mycloset.data.entities.Outfit;
import com.example.mycloset.data.entities.OutfitGarmentCrossRef;
import com.example.mycloset.data.entities.OutfitWithClothes;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface OutfitDao {
    @Insert
    List<Long> insertAll(Outfit... outfits);

    @Insert
    List<Long> insertAllCrossRef(OutfitGarmentCrossRef... outfitGarmentCrossRefs);

    @Delete
    void delete(Outfit outfit);

    @Query("SELECT * FROM Outfit")
    ListenableFuture<List<Outfit>> selectAll();

    @Transaction
    @Query("SELECT * FROM Outfit")
    ListenableFuture<List<OutfitWithClothes>> getOutfitsWithClothes();

    @Query("SELECT * FROM OutfitGarmentCrossRef")
    ListenableFuture<List<OutfitGarmentCrossRef>> getOutfitGarmentCrossRef();
}
