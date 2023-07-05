package com.example.mycloset;

import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mycloset.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_closet, R.id.navigation_calendar, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
//        startActivity(new Intent(this, SettingsActivity.class));

        BadgeDrawable profileBadge = binding.navView.getOrCreateBadge(R.id.navigation_profile);
        profileBadge.setVisible(true);
        profileBadge.setMaxCharacterCount(2);
        profileBadge.setNumber(10);

        BadgeDrawable homeBadge = binding.navView.getOrCreateBadge(R.id.navigation_home);
        homeBadge.setVisible(true);

        // Edge-to-edge experience
        // Top system bar won't be considered on screen's dimensions
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        // Bottom (navigation) system bar won't be translucent, it will take the color defined in themes
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setAppearanceLightNavigationBars(true);
    }

}