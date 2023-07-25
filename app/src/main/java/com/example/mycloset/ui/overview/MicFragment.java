package com.example.mycloset.ui.overview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.ui.overview.placeholder.PlaceholderContent;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MicFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CATEGORY = "category";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private RecyclerView recyclerView;


    private String category = "ALL";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MicFragment() {
    }

    public static MicFragment newInstance(String category) {
        return newInstance(category, 2);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MicFragment newInstance(int columnCount) {
        return newInstance("all", columnCount);
    }

    public static MicFragment newInstance(String category, int columnCount) {
        MicFragment fragment = new MicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            category = getArguments().getString(ARG_CATEGORY);

            Log.d("ARGS", mColumnCount + " " + category);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            getObjects();
//            recyclerView.setAdapter(new MyMicRecyclerViewAdapter(PlaceholderContent.ITEMS));
        }
        return view;
    }

    private void getObjects() {
        AppDatabase db = AppDatabase.get(getContext());
        GarmentDao garmentDao = db.garmentDao();

        ListenableFuture<List<Garment>> listListenableFuture;
        if (category == "ALL")
            listListenableFuture = garmentDao.selectAll();
        else
            listListenableFuture = garmentDao.selectCategory(category.toUpperCase());

        Futures.addCallback(
                listListenableFuture,
                new FutureCallback<List<Garment>>() {
                    public void onSuccess(List<Garment> result) {
                        // handle success
                        recyclerView.setAdapter(new MyMicRecyclerViewAdapter(result));
//                        String s = "";
//                        for (Garment g: result) {
//                            s += g + "\n";
//                        }
////                        result.forEach(t -> binding.textView7.setText(t.toString()));
//                        binding.textView7.setText(s);
                    }

                    public void onFailure(@NonNull Throwable thrown) {
                        // handle failure
                    }
                },
                // causes the callbacks to be executed on the main (UI) thread
                getContext().getMainExecutor()
        );
    }
}