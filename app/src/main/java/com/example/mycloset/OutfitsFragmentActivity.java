package com.example.mycloset;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.mycloset.databinding.ActivityOutfitsFragmentBinding;
import com.example.mycloset.ui.closet.OutfitsFragment;

public class OutfitsFragmentActivity extends AppCompatActivity {

    private ActivityOutfitsFragmentBinding binding;

    public OutfitsFragmentActivity(){
        super(R.layout.activity_outfits_fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityOutfitsFragmentBinding.inflate(getLayoutInflater());

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(OutfitsFragment.ARG_MODE, "SELECT");
            OutfitsFragment fragment = new OutfitsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, OutfitsFragment.class, bundle)
                    .commit();


        }



//        setContentView(binding.getRoot());


    }
}