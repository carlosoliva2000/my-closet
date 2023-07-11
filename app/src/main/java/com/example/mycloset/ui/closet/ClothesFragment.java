package com.example.mycloset.ui.closet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.AutoTransition;
import androidx.transition.Explode;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mycloset.R;
import com.example.mycloset.databinding.FragmentClothesBinding;
import com.example.mycloset.ui.anotherscroll.AnotherScrollFragment;
import com.example.mycloset.ui.calendar.CalendarFragment;
import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClothesFragment} factory method to
 * create an instance of this fragment.
 */
public class ClothesFragment extends Fragment {

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
        binding.cardTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TransitionManager.beginDelayedTransition(binding.getRoot(), new AutoTransition());
//                view.setVisibility(View.GONE);

//                TransitionManager.beginDelayedTransition((ViewGroup) getParentFragment().getView(), new Fade());
                replaceFragment();
            }
        });

        return binding.getRoot();
    }

    public void replaceFragment() {
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
                .replace(((ViewGroup) getView().getParent()).getId(), AnotherScrollFragment.class, null)
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