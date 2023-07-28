package com.example.mycloset.ui.closet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mycloset.R;
import com.example.mycloset.data.AppDatabase;
import com.example.mycloset.data.entities.Garment;
import com.example.mycloset.databinding.FragmentAddGarmentBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGarmentFragment} factory method to
 * create an instance of this fragment.
 */
public class AddGarmentFragment extends Fragment {

    private FragmentAddGarmentBinding binding;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private String category;
    private int checkedIndex;

    private String[] categoriesHumanFriendly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Registers a photo picker activity launcher in single-select mode.
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
//                    File file = new File(String.valueOf(uri.get(0)));
                    binding.imageViewGarment.setImageURI(uri);
                    binding.imageViewGarment.setTag(uri);
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
        binding = FragmentAddGarmentBinding.inflate(inflater, container, false);
        Context context = getContext();

        categoriesHumanFriendly = new String[]{
                getString(R.string.t_shirts_sing),
                getString(R.string.hoodies_sing),
                getString(R.string.sweaters_sing),
                getString(R.string.polos_sing),
                getString(R.string.shirts_sing),
                getString(R.string.jackets_sing),
                getString(R.string.coats_sing),
                getString(R.string.pants_sing),
                getString(R.string.shorts_sing),
                getString(R.string.shoes_sing),
                getString(R.string.flip_flops_sing),
                getString(R.string.hats_sing),
                getString(R.string.swimsuits_sing),
                getString(R.string.bikinis_sing)
        };

//        binding.editTextBrand.requestFocus();

        binding.buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose an animal");

                // add a radio button list
                String[] categories = {"TSHIRTS", "HOODIES", "SWEATERS", "POLOS", "SHIRTS", "JACKETS",
                "COATS", "PANTS", "SHORTS", "SHOES", "FLIPFLOPS", "HATS", "SWIMSUITS", "BIKINIS"};

                int checkedItem = 0;
                builder.setSingleChoiceItems(categoriesHumanFriendly, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user checked an item
                        checkedIndex = which;
                    }
                });

                // add OK and Cancel buttons
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                        category = categories[checkedIndex];
                        binding.buttonCategory.setText(categoriesHumanFriendly[checkedIndex]);
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.buttonCategory.getText().toString().equals(getString(R.string.select))) {
                    Toast.makeText(getContext(), "Falta establecer la categoría", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(binding.editTextBrand.getText().toString().isEmpty() || binding.editTextBrand.getText().toString().isBlank()) {
                    Toast.makeText(getContext(), "Falta establecer la marca", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(binding.imageViewGarment.getTag() == null){
                    Toast.makeText(getContext(), "Falta poner una foto", Toast.LENGTH_SHORT).show();
                    return;
                }

                Garment garment = new Garment();
                String name = binding.editTextName.getText().toString();
                if (!name.isEmpty() && !name.isBlank())
                    garment.name = name;
                garment.brand = binding.editTextBrand.getText().toString();
                garment.category = binding.buttonCategory.getText().toString();
                String colors = binding.editTextColors.getText().toString();
                if (!colors.isEmpty() && !colors.isBlank())
                    garment.colors = binding.editTextColors.getText().toString();
                garment.uri = (Uri) binding.imageViewGarment.getTag();

                AppDatabase db = AppDatabase.get(view.getContext());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        long s = 0;
//                        for (long i=0; i<200_000; i++)
//                        {
//                            s += i + i * i;
//                            Log.d("i", i+"");
//                        }
//                        garment.id = (int) s - garment.id;
                        db.garmentDao().insertAll(garment);
                        replaceFragment();
                    }
                }).start();
            }
        });

        binding.imageViewGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        return binding.getRoot();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("MIC", "MICCCCCCCCCCCCC");
//    }

    public void replaceFragment() {
        // Animations: https://developer.android.com/guide/fragments/animate

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true)
                .setCustomAnimations(
                        R.anim.slide_in,  // exit
                        R.anim.slide_out  // popExit
                )
                .remove(this)
                .commit();
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
//    public AddGarmentFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddGarmentFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddGarmentFragment newInstance(String param1, String param2) {
//        AddGarmentFragment fragment = new AddGarmentFragment();
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
//        return inflater.inflate(R.layout.fragment_add_garment, container, false);
//    }
}