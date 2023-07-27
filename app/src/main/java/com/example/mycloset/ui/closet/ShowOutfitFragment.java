package com.example.mycloset.ui.closet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.daos.OutfitDao;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.data.entities.Outfit;
import com.example.mycloset.data.entities.OutfitWithClothes;
import com.example.mycloset.databinding.FragmentShowOutfitBinding;
import com.example.mycloset.ui.overview.MyClothesGridRecyclerViewAdapter;
import com.example.mycloset.ui.overviewclothesselection.ClothesCarouselRecyclerViewAdapter;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowOutfitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowOutfitFragment extends Fragment {

    private Outfit outfit;
    private List<Garment> clothes;
    private FragmentShowOutfitBinding binding;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID_OUTFIT = "ID_OUTFIT";

    private long mIdOutfit;

    public ShowOutfitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idOutfit Parameter 1.
     * @return A new instance of fragment ShowOutfitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowOutfitFragment newInstance(Long idOutfit) {
        ShowOutfitFragment fragment = new ShowOutfitFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID_OUTFIT, idOutfit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdOutfit = getArguments().getLong(ARG_ID_OUTFIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowOutfitBinding.inflate(inflater, container, false);
        binding.carouselClothes.setLayoutManager(new CarouselLayoutManager());
        getObject();
        return binding.getRoot();
    }

    private void getObject() {
        AppDatabase db = AppDatabase.get(getContext());
        OutfitDao outfitDao = db.outfitDao();

        ListenableFuture<OutfitWithClothes> listListenableFuture = outfitDao.getOutfitWithClothesById(mIdOutfit);

        Futures.addCallback(
                listListenableFuture,
                new FutureCallback<OutfitWithClothes>() {
                    public void onSuccess(OutfitWithClothes result) {
                        // handle success
                        outfit = result.outfit;
                        clothes = result.clothes;
                        binding.textViewOutfitName.setText(outfit.name);
                        binding.imageViewShowOutfit.setImageURI(outfit.uri);
                        binding.carouselClothes.setAdapter(new ClothesCarouselRecyclerViewAdapter(clothes, binding.carouselClothes));
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