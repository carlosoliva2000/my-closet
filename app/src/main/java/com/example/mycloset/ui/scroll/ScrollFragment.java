package com.example.mycloset.ui.scroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycloset.databinding.FragmentProfileBinding;
import com.example.mycloset.databinding.FragmentScrollBinding;
import com.example.mycloset.ui.scroll.ScrollViewModel;

public class ScrollFragment extends Fragment {

    private ScrollViewModel mViewModel;
    private FragmentScrollBinding binding;

    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ScrollViewModel.class);
        binding = FragmentScrollBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putIntArray("SCROLL_POSITION",
//                new int[]{ binding..getScrollX(), mScrollView.getScrollY()});
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}