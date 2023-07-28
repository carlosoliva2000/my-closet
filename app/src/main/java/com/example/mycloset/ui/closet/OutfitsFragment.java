package com.example.mycloset.ui.closet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.entities.Outfit;
import com.example.mycloset.data.entities.OutfitGarmentCrossRef;
import com.example.mycloset.data.entities.OutfitWithClothes;
import com.example.mycloset.databinding.FragmentOutfitsBinding;
import com.example.mycloset.ui.overview.OutfitsGridRecyclerViewAdapter;
import com.example.mycloset.utils.FragmentUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutfitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutfitsFragment extends Fragment {

    private FragmentOutfitsBinding binding;
    private String mode = "READ";  // READ or SELECT
    private List<Outfit> outfits;
    private OutfitsGridRecyclerViewAdapter outfitsGridRecyclerViewAdapter;

    public OutfitsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMode = getArguments().getString(ARG_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mMode = getArguments().getString(ARG_MODE);
        }
//        if (getArguments() != null) {
//            String mode = requireArguments().getString(ARG_MODE);
//            Log.d("MODE???", mode + "");
//            if (mode != null && !mode.isEmpty()) {
//                mMode = mode;
//            }
//        }

        binding = FragmentOutfitsBinding.inflate(inflater, container, false);

        binding.floatingActionButtonOutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(AddOutfitFragment.class);
            }
        });

        outfits = new ArrayList<>();
        outfitsGridRecyclerViewAdapter = new OutfitsGridRecyclerViewAdapter(outfits, "READ", this);
        binding.recyclerViewListOutfits.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewListOutfits.setAdapter(outfitsGridRecyclerViewAdapter);

        AppDatabase db = AppDatabase.get(getContext());

        ListenableFuture<List<OutfitWithClothes>> listListenableFuture = db.outfitDao().getOutfitsWithClothes();
        Futures.addCallback(
                listListenableFuture,
                new FutureCallback<List<OutfitWithClothes>>() {
                    public void onSuccess(List<OutfitWithClothes> result) {
                        // handle success
                        for (OutfitWithClothes outfitWithClothes: result)
                            Log.d("OUTFIT CON CLOTHES", outfitWithClothes.toString());
                    }

                    public void onFailure(@NonNull Throwable thrown) {
                        // handle failure
                        Log.e("CUIDADO", thrown.toString());
                    }
                },
                // causes the callbacks to be executed on the main (UI) thread
                getContext().getMainExecutor()
        );

        ListenableFuture<List<Outfit>> listListenableFuture2 = db.outfitDao().selectAll();
        Futures.addCallback(
                listListenableFuture2,
                new FutureCallback<List<Outfit>>() {
                    public void onSuccess(List<Outfit> result) {
                        // handle success
                        if (result == null || result.size() == 0) {
                            binding.recyclerViewListOutfits.setVisibility(View.GONE);
                            binding.textView2.setVisibility(View.VISIBLE);
                        }
                        else {
                            binding.textView2.setVisibility(View.GONE);
                            binding.recyclerViewListOutfits.setVisibility(View.VISIBLE);
                            outfitsGridRecyclerViewAdapter.setmValues(result);
                            binding.recyclerViewListOutfits.setAdapter(outfitsGridRecyclerViewAdapter);
                            for (Outfit outfitWithClothes : result)
                                Log.d("OUTFITS", outfitWithClothes.toString());
                        }
                    }

                    public void onFailure(@NonNull Throwable thrown) {
                        // handle failure
                        Log.e("CUIDADO", thrown.toString());
                    }
                },
                // causes the callbacks to be executed on the main (UI) thread
                getContext().getMainExecutor()
        );

        ListenableFuture<List<OutfitGarmentCrossRef>> listListenableFuture3 = db.outfitDao().getOutfitGarmentCrossRef();
        Futures.addCallback(
                listListenableFuture3,
                new FutureCallback<List<OutfitGarmentCrossRef>>() {
                    public void onSuccess(List<OutfitGarmentCrossRef> result) {
                        // handle success
                        for (OutfitGarmentCrossRef outfitWithClothes : result)
                            Log.d("CROSSREF", outfitWithClothes.toString());
                    }

                    public void onFailure(@NonNull Throwable thrown) {
                        // handle failure
                        Log.e("CUIDADO", thrown.toString());
                    }
                },
                // causes the callbacks to be executed on the main (UI) thread
                getContext().getMainExecutor()
        );

        return binding.getRoot();
    }

    public void replaceFragment(Class<? extends androidx.fragment.app.Fragment> fragment) {
        // Animations: https://developer.android.com/guide/fragments/animate
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true)
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                )
                .replace(((ViewGroup) getView().getParent()).getId(), fragment, null)
                .addToBackStack(null)
                .commit();

        //        transaction.setCustomAnimations(androidx.transition.R.anim.abc_slide_in_bottom, androidx.transition.R.anim.abc_slide_out_bottom,
//                androidx.transition.R.anim.abc_slide_in_top, androidx.transition.R.anim.abc_slide_out_top);
    }

    public static class OnOutfitClicked implements View.OnClickListener {
        private Outfit mItem;
        private OutfitsFragment mFragment;

        public OnOutfitClicked(Outfit outfit, OutfitsFragment fragment) {
            mItem = outfit;
            mFragment = fragment;
        }

        @Override
        public void onClick(View view) {
            Log.d("CLICK!", mItem.toString());
            if (mFragment.mode.equals("READ")) {
                Log.d("MODO READ", "Desplegar tab...");
                FragmentUtils.replaceFragment(mFragment, ShowOutfitFragment.newInstance(mItem.outfitId));
            }
            else {
                Log.d("MODO SELECT", "Terminar fragment y devolver resultado!");
                Bundle result = new Bundle();
                result.putLong("OUTFIT_ID_SELECT", mItem.outfitId);
                mFragment.getParentFragmentManager().setFragmentResult("OUTFIT_ID_SELECTED", result);
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

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_MODE = "mode";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
    private String mMode;
//    private String mParam2;
//
//    public OutfitsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment OutfitsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
    public static OutfitsFragment newInstance(String mode) {
        OutfitsFragment fragment = new OutfitsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_outfits, container, false);
//    }
}