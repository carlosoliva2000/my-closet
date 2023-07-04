package com.example.mycloset.ui.closet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycloset.databinding.FragmentClosetBinding;

public class ClosetFragment extends Fragment {

    private FragmentClosetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClosetViewModel closetViewModel =
                new ViewModelProvider(this).get(ClosetViewModel.class);

        binding = FragmentClosetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCloset;
        closetViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}