package com.example.mycloset.ui.overview;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.ui.overview.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.mycloset.databinding.FragmentItemBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMicRecyclerViewAdapter extends RecyclerView.Adapter<MyMicRecyclerViewAdapter.ViewHolder> {

    private final List<Garment> mValues;

    public MyMicRecyclerViewAdapter(List<Garment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Garment garment = mValues.get(position);
        holder.mItem = garment;
        try {
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
        public Garment mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mImageView = binding.cardImageItem;
            mContent = binding.cardTextItem;
//            mIdView = binding.itemNumber;
//            mContentView = binding.content;
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}