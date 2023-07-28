package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycloset.databinding.ActivityFirstTimeBinding;

public class FirstTimeActivity extends AppCompatActivity {

    private ActivityFirstTimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFirstTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(binding.editTextText.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void register(String name) {
        if (name.isEmpty() || name.isBlank()) {
            Toast.makeText(this, getString(R.string.sign_up_no_name), Toast.LENGTH_SHORT).show();
        }
        else if(name.length() < 2) {
            Toast.makeText(this, getString(R.string.sign_up_name_alike), Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("SIGNED_UP", true);
            editor.putString("USERNAME", name);
            editor.apply();
            finish();
        }
    }
}