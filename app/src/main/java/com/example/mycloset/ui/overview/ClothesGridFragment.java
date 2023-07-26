package com.example.mycloset.ui.overview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.android.material.card.MaterialCardView;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ClothesGridFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_MODE = "mode";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private RecyclerView recyclerView;
    private String category = "ALL";
    private String mode = "READ";  // READ or SELECT
    private List<Garment> clothes = new ArrayList<>();
    private MyClothesGridRecyclerViewAdapter myClothesGridRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClothesGridFragment() {
    }

    public static ClothesGridFragment newInstance(String category, String mode) {
        return newInstance(category, 2, mode);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ClothesGridFragment newInstance(int columnCount, String mode) {
        return newInstance("ALL", columnCount, mode);
    }

    public static ClothesGridFragment newInstance(String category, int columnCount, String mode) {
        ClothesGridFragment fragment = new ClothesGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            category = getArguments().getString(ARG_CATEGORY);
            mode = getArguments().getString(ARG_MODE);
            Log.d("ARGS", "Showing " + category + " items in " + mode + " mode with " + mColumnCount + " columns");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothes_grid, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            myClothesGridRecyclerViewAdapter = new MyClothesGridRecyclerViewAdapter(clothes, mode, this);
            recyclerView.setAdapter(myClothesGridRecyclerViewAdapter);
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
                        if (result==null || result.size()==0) {
                            // TODO create msg for empty category!
                        }
                        myClothesGridRecyclerViewAdapter.setmValues(result);
                        recyclerView.setAdapter(myClothesGridRecyclerViewAdapter);
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

    public static class OnClothesClicked implements View.OnClickListener {

        private Garment mItem;
        private ClothesGridFragment mFragment;

        public OnClothesClicked(Garment garment, ClothesGridFragment fragment) {
            mItem = garment;
            mFragment = fragment;
        }

        @Override
        public void onClick(View view) {
            Log.d("CLICK!", mItem.toString());
            if (mFragment.mode.equals("READ")) {
                Log.d("MODO READ", "Desplegar tab...");
            }
            else {
                Log.d("MODO SELECT", "Terminar fragment y devolver resultado!");
                Bundle result = new Bundle();
                result.putLong("ID_SELECTED", mItem.garmentId);
                mFragment.getParentFragmentManager().setFragmentResult("ID_SELECTED", result);
                FragmentTransaction transaction = mFragment.getParentFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true)
                        .setCustomAnimations(
                                R.anim.slide_in,  // exit
                                R.anim.slide_out  // popExit
                        )
                        .remove(mFragment)
                        .commit();
            }
        }
    }
}