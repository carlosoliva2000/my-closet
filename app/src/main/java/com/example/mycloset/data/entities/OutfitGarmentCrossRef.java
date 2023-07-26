package com.example.mycloset.data.entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"garmentId", "outfitId"})
public class OutfitGarmentCrossRef {
    public long outfitId;
    public long garmentId;

//    public OutfitGarmentCrossRef(int outfitId, int garmentId) {
//        this.outfitId = outfitId;
//        this.garmentId = garmentId;
//    }

//    public OutfitGarmentCrossRef() {
//
//    }

    @Override
    public String toString() {
        return "OutfitGarmentCrossRef{" +
                "outfitId=" + outfitId +
                ", garmentId=" + garmentId +
                '}';
    }
}
