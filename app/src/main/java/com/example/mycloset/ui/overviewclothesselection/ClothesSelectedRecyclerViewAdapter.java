package com.example.mycloset.ui.overviewclothesselection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.databinding.FragmentClothesChoosenItemBinding;
import com.example.mycloset.databinding.FragmentClothesItemBinding;
import com.example.mycloset.ui.closet.AddOutfitFragment;
import com.example.mycloset.ui.overview.MyClothesGridRecyclerViewAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ClothesSelectedRecyclerViewAdapter extends RecyclerView.Adapter<ClothesSelectedRecyclerViewAdapter.ViewHolder> {

    private List<Garment> mValues;
    private RecyclerView recyclerView;

    public ClothesSelectedRecyclerViewAdapter(List<Garment> items, RecyclerView carousel) {
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
        return new ViewHolder(FragmentClothesChoosenItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Garment garment = mValues.get(position);
        holder.mItem = garment;
        try {
            holder.mImageView.setImageURI(garment.uri);
            holder.mContent.setText(garment.brand + " " + garment.category);
            holder.mButton.setOnClickListener(new AddOutfitFragment.OnDeleteGarmentOnSelection(garment, recyclerView, this, mValues, recyclerView));
//            holder.mButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mValues.remove(garment);
//                    recyclerView.setAdapter(thisObject);
//                }
//            });
        }
        catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
//        if (isEmpty) {
//            mValues.add(new Garment());
//            return 1;
//        }
//        else
            return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final ShapeableImageView mImageView;
        public final TextView mContent;
        public final Button mButton;
        public final MaterialCardView mCard;
        public Garment mItem;

        public ViewHolder(FragmentClothesChoosenItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.cardImageItem;
            mContent = binding.cardTextItem;
            mButton = binding.button;
            mCard = binding.cardItem;
//            mIdView = binding.itemNumber;
//            mContentView = binding.content;
        }
    }
}
