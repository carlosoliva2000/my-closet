package com.example.mycloset.ui.overviewclothesselection;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.databinding.FragmentClothesChoosenItemBinding;
import com.example.mycloset.databinding.FragmentClothesShowItemBinding;
import com.example.mycloset.ui.closet.AddOutfitFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ClothesCarouselRecyclerViewAdapter extends RecyclerView.Adapter<ClothesCarouselRecyclerViewAdapter.ViewHolder>{

    private List<Garment> mValues;
    private RecyclerView recyclerView;

    public ClothesCarouselRecyclerViewAdapter(List<Garment> items, RecyclerView carousel) {
        mValues = items;
        recyclerView = carousel;
    }

    public List<Garment> getmValues() {
        return mValues;
    }

    public void setmValues(List<Garment> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentClothesShowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Garment garment = mValues.get(position);
        holder.mItem = garment;
        try {
            holder.mImageView.setImageURI(garment.uri);
            holder.mContent.setText(garment.getFullString());
        }
        catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public final ShapeableImageView mImageView;
        public final TextView mContent;
        public final MaterialCardView mCard;
        public Garment mItem;

        public ViewHolder(FragmentClothesShowItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.cardClothesImageItem;
            mContent = binding.cardClothesTextItem;
            mCard = binding.cardClothesItem;
        }
    }
}
