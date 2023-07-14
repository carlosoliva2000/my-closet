package com.example.mycloset.ui.home;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycloset.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.util.UUID;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private boolean canShowAccounts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        StorageManager storageManager = getContext().
                getApplicationContext().getSystemService(StorageManager.class);
        UUID appSpecificInternalDirUuid = null;
        try {
            appSpecificInternalDirUuid = storageManager.getUuidForPath(getContext().getExternalFilesDir(null));
            @SuppressLint("WrongThread") long availableBytes =
                    storageManager.getAllocatableBytes(appSpecificInternalDirUuid) / 1024L / 1024L / 1024L;
            binding.textView6.setText(availableBytes+" GB");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        binding.textView4.setText(getContext().getExternalFilesDir(null).toString());
        canShowAccounts = (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED);
        ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                canShowAccounts = true;
            }
            else {
                canShowAccounts = false;
                binding.textView4.setText("No hay permisos :(");
            }
        });

        if (!canShowAccounts)
        {
            requestPermissionLauncher.launch(Manifest.permission.GET_ACCOUNTS);
        }
        else{
            showAccounts();
        }



    }

    private void showAccounts(){
        AccountManager accountManager = AccountManager.get(getContext());
        Account[] accounts = accountManager.getAccountsByType("com.google");
        String s = "";
        for(Account a: accounts) {
            s += a.toString() + "\n";
        }
        binding.textView4.setText(accounts.length + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
}