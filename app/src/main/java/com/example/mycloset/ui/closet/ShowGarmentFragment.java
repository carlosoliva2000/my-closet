package com.example.mycloset.ui.closet;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.daos.GarmentDao;
import com.example.mycloset.data.daos.OutfitDao;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.databinding.FragmentShowGarmentBinding;
import com.example.mycloset.utils.FragmentUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowGarmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowGarmentFragment extends Fragment {

    private FragmentShowGarmentBinding binding;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID_GARMENT = "ID_GARMENT";

    private long mIdGarment;
    private Garment garment;

    public ShowGarmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idGarment Parameter 1.
     * @return A new instance of fragment ShowGarmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowGarmentFragment newInstance(Long idGarment) {
        ShowGarmentFragment fragment = new ShowGarmentFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID_GARMENT, idGarment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdGarment = getArguments().getLong(ARG_ID_GARMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShowGarmentBinding.inflate(inflater, container, false);

        getObject();

        binding.buttonDeleteGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO delete garment with ease...
                AppDatabase db = AppDatabase.get(getContext());
                GarmentDao garmentDao = db.garmentDao();
                OutfitDao outfitDao = db.outfitDao();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        garment.isActive = false;
                        garmentDao.update(garment);
//                        garmentDao.delete(garment);

                        // Check if this garment is a outfit, if that's the case, delete it the reference
                        outfitDao.deleteCrossRef(garment.garmentId);
                    }
                }).start();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.fade_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.slide_out  // popExit
                        )
                        .replace(((ViewGroup)ShowGarmentFragment.this.getView().getParent()).getId(), ClothesFragment.class, null)
                        .commit();
                fragmentManager.popBackStack();
//                FragmentUtils.replaceFragment(ShowGarmentFragment.this, ClothesFragment.class);

            }
        });

        return binding.getRoot();
    }

    private void getObject() {
        AppDatabase db = AppDatabase.get(getContext());
        GarmentDao garmentDao = db.garmentDao();

        ListenableFuture<Garment> listListenableFuture = garmentDao.selectById(mIdGarment);

        Futures.addCallback(
                listListenableFuture,
                new FutureCallback<Garment>() {
                    public void onSuccess(Garment result) {
                        // handle success
                        garment = result;
                        binding.textViewGarmentFullName.setText(result.category+" "+result.brand);
                        binding.imageViewShowGarment.setImageURI(result.uri);
                        binding.textViewShowBrand.setText(result.brand);
                        binding.textViewShowCategory.setText(result.category);
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