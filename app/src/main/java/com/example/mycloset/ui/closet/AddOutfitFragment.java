package com.example.mycloset.ui.closet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.transition.AutoTransition;
import androidx.transition.Explode;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.data.entities.Outfit;
import com.example.mycloset.data.entities.OutfitGarmentCrossRef;
import com.example.mycloset.data.entities.OutfitWithClothes;
import com.example.mycloset.databinding.FragmentAddOutfitBinding;
import com.example.mycloset.ui.overview.ClothesGridFragment;
import com.example.mycloset.ui.overviewclothesselection.ClothesSelectedRecyclerViewAdapter;
import com.example.mycloset.utils.FragmentUtils;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddOutfitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOutfitFragment extends Fragment {

    private FragmentAddOutfitBinding binding;
    private ClothesSelectedRecyclerViewAdapter clothesSelectedRecyclerViewAdapter;
    private List<Garment> clothes;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clothes = new ArrayList<>();
        getParentFragmentManager().setFragmentResultListener("ID_SELECTED", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                long idSelected = result.getLong("ID_SELECTED");

                // Access to the DB to get the garment selected
                AppDatabase db = AppDatabase.get(getContext());
                GarmentDao garmentDao = db.garmentDao();

                ListenableFuture<Garment> listListenableFuture = garmentDao.selectById(idSelected);
                Futures.addCallback(
                        listListenableFuture,
                        new FutureCallback<Garment>() {
                            public void onSuccess(Garment result) {
                                // handle success

                                clothes.add(result);
                                //                            clothesSelectedRecyclerViewAdapter.getmValues().add(garment);
                                //                        clothesSelectedRecyclerViewAdapter.setmValues(result);
                                TransitionManager.beginDelayedTransition((ViewGroup) binding.carouselRecyclerView, new Slide(Gravity.LEFT));
                                binding.carouselRecyclerView.setVisibility(View.VISIBLE);
                                binding.carouselRecyclerView.setAdapter(clothesSelectedRecyclerViewAdapter);
                                Log.d("EEEEEEEEEEEEEEEEEEEE", "ESTAMOS AQUÍ!!!!");
                                Log.d("Estado del array", clothes.toString());
                            }

                            public void onFailure(@NonNull Throwable thrown) {
                                // handle failure
                            }
                        },
                        // causes the callbacks to be executed on the main (UI) thread
                        getContext().getMainExecutor()
                );
            }
        });

        // Registers a photo picker activity launcher in single-select mode.
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
//                    File file = new File(String.valueOf(uri.get(0)));
                    binding.imageViewOutfitPicture.setImageURI(uri);
                    binding.imageViewOutfitPicture.setTag(uri);
                    int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContext().getContentResolver().takePersistableUriPermission(uri, flag);  // ATTENTION TO THIS LINE!!! Crash when showing clothes if not present
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddOutfitBinding.inflate(inflater, container, false);

//        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager();
        binding.carouselRecyclerView.setLayoutManager(new CarouselLayoutManager());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView);
        clothesSelectedRecyclerViewAdapter = new ClothesSelectedRecyclerViewAdapter(clothes, binding.carouselRecyclerView);
        binding.carouselRecyclerView.setAdapter(clothesSelectedRecyclerViewAdapter);
//        getObjects();

        binding.carouselRecyclerView.setVisibility(View.GONE);

        binding.buttonAddGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(ClothesGridFragment.newInstance("ALL", "SELECT"));
//                getObjects();
            }
        });
//        binding.carouselRecyclerView.setAdapter(new MyClothesGridRecyclerViewAdapter());

        binding.imageViewOutfitPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        binding.buttonSaveOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = AppDatabase.get(view.getContext());

                        Outfit outfit = new Outfit();
                        outfit.name = binding.editTextOutfitName.getText().toString();
                        outfit.uri = (Uri) binding.imageViewOutfitPicture.getTag();
//                        );

                        Log.d("NUEVO OUTFIT (PRE)", outfit.toString());
                        long idOutfit = db.outfitDao().insertAll(outfit).get(0);
                        Log.d("NUEVO OUTFIT (POST)", "ID NUEVO: " + idOutfit);


                        Log.d("GUARDAR", "Mi lista: " + clothes);
                        for (Garment garment: clothes) {
                            OutfitGarmentCrossRef outfitGarmentCrossRef = new OutfitGarmentCrossRef();
                            outfitGarmentCrossRef.outfitId = idOutfit;
                            outfitGarmentCrossRef.garmentId = garment.garmentId;
                            db.outfitDao().insertAllCrossRef(outfitGarmentCrossRef);
                        }

                        FragmentUtils.replaceFragment(AddOutfitFragment.this, OutfitsFragment.class);
//                        FragmentUtils.removeFragment(AddOutfitFragment.this);
                    }
                }).start();
            }
        });

        binding.buttonDiscardOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.removeFragment(AddOutfitFragment.this);
            }
        });

        return binding.getRoot();
    }

    private void getObjects() {
        AppDatabase db = AppDatabase.get(getContext());
        GarmentDao garmentDao = db.garmentDao();

        ListenableFuture<List<Garment>> listListenableFuture = garmentDao.selectAll();

        Futures.addCallback(
                listListenableFuture,
                new FutureCallback<List<Garment>>() {
                    public void onSuccess(List<Garment> result) {
//                        if (new Random().nextDouble() > 0.1) {
                        // handle success

                        Garment garment = result.get(new Random().nextInt(result.size()));
                        clothes.add(garment);
    //                            clothesSelectedRecyclerViewAdapter.getmValues().add(garment);
    //                        clothesSelectedRecyclerViewAdapter.setmValues(result);
                        TransitionManager.beginDelayedTransition((ViewGroup) binding.carouselRecyclerView, new Slide(Gravity.LEFT));
                        binding.carouselRecyclerView.setVisibility(View.VISIBLE);
                        binding.carouselRecyclerView.setAdapter(clothesSelectedRecyclerViewAdapter);


//                        }
//                        else {
//                            if (clothesSelectedRecyclerViewAdapter.getItemCount() > 0) {
//                                clothes.remove(0);
////                                clothesSelectedRecyclerViewAdapter.getmValues().remove(0);
//                                binding.carouselRecyclerView.setAdapter(clothesSelectedRecyclerViewAdapter);
//                            }
//                        }
//
//                        if (clothesSelectedRecyclerViewAdapter.getItemCount() > 0)
//                            binding.carouselRecyclerView.setVisibility(View.VISIBLE);
//                        else
//                            binding.carouselRecyclerView.setVisibility(View.GONE);


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

    public void replaceFragment(Fragment fragment) {
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
                .add(((ViewGroup) getView().getParent()).getId(), fragment, null)
                .addToBackStack(null)
                .commit();

        //        transaction.setCustomAnimations(androidx.transition.R.anim.abc_slide_in_bottom, androidx.transition.R.anim.abc_slide_out_bottom,
//                androidx.transition.R.anim.abc_slide_in_top, androidx.transition.R.anim.abc_slide_out_top);
    }

     public static class OnDeleteGarmentOnSelection implements View.OnClickListener {

        private Garment garment;
        private RecyclerView carousel;
        private RecyclerView.Adapter adapter;
        private List<Garment> clothes;
        private View view;

        public OnDeleteGarmentOnSelection(Garment garment, RecyclerView carousel, RecyclerView.Adapter adapter, List<Garment> clothes, View view) {
            this.garment = garment;
            this.carousel = carousel;
            this.adapter = adapter;
            this.clothes = clothes;
            this.view = view;
        }

        @Override
        public void onClick(View view) {
            clothes.remove(garment);
            TransitionManager.beginDelayedTransition((ViewGroup) this.view, new AutoTransition());
            this.view.setVisibility(View.GONE);
            carousel.setAdapter(adapter);
            carousel.setVisibility(clothes.size() > 0 ? View.VISIBLE : View.GONE);
        }
    }

    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AddOutfitFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddOutfitFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddOutfitFragment newInstance(String param1, String param2) {
//        AddOutfitFragment fragment = new AddOutfitFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_outfit, container, false);
//    }
}