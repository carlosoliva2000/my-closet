package com.example.mycloset.data.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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

    @Update
    void update(Outfit outfit);

    @Delete
    void delete(Outfit outfit);

    @Query("DELETE FROM OutfitGarmentCrossRef WHERE garmentId = :idGarment")
    void deleteCrossRef(long idGarment);

    @Query("SELECT * FROM Outfit WHERE isActive")
    ListenableFuture<List<Outfit>> selectAll();

    @Query("SELECT * FROM Outfit WHERE outfitId=:id")
    ListenableFuture<Outfit> selectById(long id);

    @Transaction
    @Query("SELECT * FROM Outfit")
    ListenableFuture<List<OutfitWithClothes>> getOutfitsWithClothes();

    @Transaction
    @Query("SELECT * FROM Outfit WHERE outfitId=:id")
    ListenableFuture<OutfitWithClothes> getOutfitWithClothesById(long id);

    @Query("SELECT * FROM OutfitGarmentCrossRef")
    ListenableFuture<List<OutfitGarmentCrossRef>> getOutfitGarmentCrossRef();
}
