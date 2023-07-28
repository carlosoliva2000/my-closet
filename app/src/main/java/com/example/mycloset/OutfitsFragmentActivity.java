package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mycloset.databinding.ActivityOutfitsFragmentBinding;
import com.example.mycloset.ui.closet.OutfitsFragment;

public class OutfitsFragmentActivity extends AppCompatActivity {

    private ActivityOutfitsFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOutfitsFragmentBinding.inflate(getLayoutInflater());

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(OutfitsFragment.ARG_MODE, "SELECT");
            OutfitsFragment fragment = new OutfitsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, fragment)
                    .commit();
        }



        setContentView(binding.getRoot());
    }
}