package com.example.mycloset.ui.overview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.data.entities.Outfit;
import com.example.mycloset.databinding.FragmentClothesChoosenItemBinding;
import com.example.mycloset.databinding.FragmentClothesItemBinding;
import com.example.mycloset.ui.closet.OutfitsFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class OutfitsGridRecyclerViewAdapter extends RecyclerView.Adapter<OutfitsGridRecyclerViewAdapter.ViewHolder> {

    private List<Outfit> mValues;
    private final String mode;  // Either "READ" or "SELECT"
    private final OutfitsFragment fragment;

    public OutfitsGridRecyclerViewAdapter(List<Outfit> values, String mode, OutfitsFragment fragment) {
        mValues = values;
        this.mode = mode;
        this.fragment = fragment;
    }

    public List<Outfit> getmValues() {
        return mValues;
    }

    public void setmValues(List<Outfit> mValues) {
        this.mValues = mValues;
    }

    @Override
    public OutfitsGridRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OutfitsGridRecyclerViewAdapter.ViewHolder(FragmentClothesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final OutfitsGridRecyclerViewAdapter.ViewHolder holder, int position) {
        Outfit outfit = mValues.get(position);
        holder.mItem = outfit;
        try {
            holder.mCard.setTag(outfit);
            holder.mCard.setOnClickListener(new OutfitsFragment.OnOutfitClicked(outfit, fragment));
            holder.mImageView.setImageURI(outfit.uri);
            holder.mContent.setText(outfit.name);
        }
        catch (Exception ignored) {

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
        public Outfit mItem;

        public ViewHolder(FragmentClothesItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.cardImageItem;
            mContent = binding.cardTextItem;
            mCard = binding.cardItem;
        }
    }
}
