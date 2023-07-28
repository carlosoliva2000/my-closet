package com.example.mycloset.ui.closet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycloset.R;
import com.example.mycloset.databinding.FragmentClosetBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ClosetFragment extends Fragment {

    private FragmentClosetBinding binding;
    ViewPager2 viewPager;
    ClosetStateAdapter closetStateAdapter;
    TabLayout tabLayout;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    //    private ClosetCollectionAdapter closetCollectionAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_closet, container, false);
        ClosetViewModel closetViewModel =
                new ViewModelProvider(this).get(ClosetViewModel.class);

        binding = FragmentClosetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textCloset;
//        closetViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return inflater.inflate(R.layout.fragment_closet, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Swipe views/tabs: https://hmkcode.com/android/android-creating-swipe-views-tabs/

//        closetCollectionAdapter = new ClosetCollectionAdapter(this);
        viewPager = binding.pager; // Or view.findViewById(R.id.pager); if return inflater.inflate(R.layout.fragment_closet, container, false);
        closetStateAdapter = new ClosetStateAdapter(this);
        viewPager.setAdapter(closetStateAdapter);

        tabLayout = binding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //TODO: arreglar esto que está hardcodeado!!!
                if (position == 0) {
                    tab.setText(getString(R.string.clothes));
                    tab.setIcon(R.drawable.ic_dashboard_black_24dp);
                }
                else if (position == 1) {
                    tab.setText(getString(R.string.outfits));
                    tab.setIcon(android.R.drawable.btn_star);
                }
                else
                    tab.setText("ERROR?");
            }
        }).attach();
//        new TabLayoutMediator(binding.tabLayout, viewPager,
//                (tab, position) -> tab.setText("OBJECT " + (position + 1))
//        ).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


// Lo de abajo está cogido de la página de Developer de Android pero es basura, ignorar...

//class ClosetCollectionAdapter extends FragmentStateAdapter {
//    public ClosetCollectionAdapter(Fragment fragment) {
//        super(fragment);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        // Return a NEW fragment instance in createFragment(int)
//        Fragment fragment = new ClosetObjectFragment();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(ClosetObjectFragment.ARG_OBJECT, position + 1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return 3;
//    }
//}
//
//
//class ClosetObjectFragment extends Fragment {
//    public static final String ARG_OBJECT = "object";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Bundle args = getArguments();
//        ((TextView) view.findViewById(R.id.text_home))
//                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
//    }
//}