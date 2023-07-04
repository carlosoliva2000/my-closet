package com.example.mycloset.ui.anotherscroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycloset.databinding.FragmentAnotherScrollingBinding;

public class AnotherScrollFragment extends Fragment {

    private AnotherScrollViewModel mViewModel;
    private FragmentAnotherScrollingBinding binding;

    public static AnotherScrollFragment newInstance() {
        return new AnotherScrollFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AnotherScrollViewModel.class);
        binding = FragmentAnotherScrollingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}