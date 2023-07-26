package com.example.mycloset.ui.overview;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.databinding.FragmentClothesItemBinding;
import com.example.mycloset.ui.overview.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyClothesGridRecyclerViewAdapter extends RecyclerView.Adapter<MyClothesGridRecyclerViewAdapter.ViewHolder> {

    private List<Garment> mValues;
    private final String mode;  // Either "READ" or "SELECT"
    private final ClothesGridFragment fragment;

    public MyClothesGridRecyclerViewAdapter(List<Garment> values, String mode, ClothesGridFragment fragment) {
        mValues = values;
        this.mode = mode;
        this.fragment = fragment;
    }

    public List<Garment> getmValues() {
        return mValues;
    }

    public void setmValues(List<Garment> mValues) {
        this.mValues = mValues;
    }

    //    public MyClothesGridRecyclerViewAdapter(List<Garment> items) {
//        mValues = items;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentClothesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Garment garment = mValues.get(position);
        holder.mItem = garment;
        try {
            holder.mCard.setTag(garment);
//            holder.mCard.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("CLICK!", holder.mItem.toString());
//                    if (mode.equals("READ")) {
//                        Log.d("MODO READ", "Desplegar tab...");
//                    }
//                    else {
//                        Log.d("MODO SELECT", "Terminar fragment y devolver resultado!");
//                        Bundle result = new Bundle();
//                        result.putInt("ID_SELECTED", holder.mItem.id);
//                        getParent
//                    }
//                }
//            });
            holder.mCard.setOnClickListener(new ClothesGridFragment.OnClothesClicked(garment, fragment));
            holder.mImageView.setImageURI(garment.uri);
            holder.mContent.setText(garment.brand + " " + garment.category);
        }
        catch (Exception e) {

        }
//        holder.mIdView.setText(mValues.get(position).category);
//        holder.mContentView.setText(mValues.get(position).brand);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
//        public final TextView mContentView;
        public final ShapeableImageView mImageView;
        public final TextView mContent;
        public final MaterialCardView mCard;
        public Garment mItem;

        public ViewHolder(FragmentClothesItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.cardImageItem;
            mContent = binding.cardTextItem;
            mCard = binding.cardItem;
//            mIdView = binding.itemNumber;
//            mContentView = binding.content;
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}