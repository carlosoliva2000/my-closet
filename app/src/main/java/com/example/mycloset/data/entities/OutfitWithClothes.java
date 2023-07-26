package com.example.mycloset.data.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class OutfitWithClothes {
    @Embedded
    public Outfit outfit;

    @Relation(parentColumn = "outfitId", entityColumn = "garmentId", associateBy = @Junction(OutfitGarmentCrossRef.class), entity = Garment.class)
    public List<Garment> clothes;

    @Override
    public String toString() {
        return "OutfitWithClothes{" +
                "outfit=" + outfit +
                ", clothes=" + clothes +
                '}';
    }
}
