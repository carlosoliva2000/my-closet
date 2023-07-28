package com.example.mycloset.ui.closet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycloset.R;
import com.example.mycloset.databinding.FragmentClothesBinding;
import com.example.mycloset.ui.overview.ClothesGridFragment;
import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClothesFragment} factory method to
 * create an instance of this fragment.
 */
public class ClothesFragment extends Fragment {

    class CardListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            replaceFragment(ClothesGridFragment.newInstance((String) view.getTag(), 2, "READ"));
        }
    }

    private FragmentClothesBinding binding;
    private MaterialCardView cardView;

    public ClothesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
//        View inflate = inflater.inflate(R.layout.fragment_clothes, container, false);
//        inflate.findViewById(R.id.card_todo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("MYLISTENER", "Click!");
//                Toast.makeText(view.getContext(), "MOOOOC?", Toast.LENGTH_LONG).show();
//            }
//        });

        binding = FragmentClothesBinding.inflate(inflater, container, false);

        CardListener cardListener = new CardListener();

        binding.cardTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TransitionManager.beginDelayedTransition(binding.getRoot(), new AutoTransition());
//                view.setVisibility(View.GONE);

//                TransitionManager.beginDelayedTransition((ViewGroup) getParentFragment().getView(), new Fade());
                replaceFragment(ClothesGridFragment.newInstance("ALL", 3, "READ"));
            }
        });
        binding.cardBikinis.setOnClickListener(cardListener);
        binding.cardCoats.setOnClickListener(cardListener);
        binding.cardFlipflops.setOnClickListener(cardListener);
        binding.cardHats.setOnClickListener(cardListener);
        binding.cardHoodies.setOnClickListener(cardListener);
        binding.cardJackets.setOnClickListener(cardListener);
        binding.cardPants.setOnClickListener(cardListener);
        binding.cardPolos.setOnClickListener(cardListener);
        binding.cardShirts.setOnClickListener(cardListener);
        binding.cardShoes.setOnClickListener(cardListener);
        binding.cardShorts.setOnClickListener(cardListener);
        binding.cardSweaters.setOnClickListener(cardListener);
        binding.cardSwimsuits.setOnClickListener(cardListener);
        binding.cardTshirts.setOnClickListener(cardListener);

        binding.fabClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(AddGarmentFragment.class);
            }
        });

        return binding.getRoot();
    }

    public void onCardClick(View view) {
        replaceFragment(ClothesGridFragment.newInstance((String) view.getTag(), "READ"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().getParent()
//        FloatingActionButton fab = getActivity().findViewById(R.id.floatingActionButton2);
//        fab.setVisibility(View.VISIBLE);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment();
//            }
//        });
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
                .replace(((ViewGroup) getView().getParent()).getId(), fragment, null)
                .addToBackStack(null)
                .commit();

        //        transaction.setCustomAnimations(androidx.transition.R.anim.abc_slide_in_bottom, androidx.transition.R.anim.abc_slide_out_bottom,
//                androidx.transition.R.anim.abc_slide_in_top, androidx.transition.R.anim.abc_slide_out_top);
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

    //    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ClothesFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ClothesFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ClothesFragment newInstance(String param1, String param2) {
//        ClothesFragment fragment = new ClothesFragment();
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
//        return inflater.inflate(R.layout.fragment_clothes, container, false);
//    }
}