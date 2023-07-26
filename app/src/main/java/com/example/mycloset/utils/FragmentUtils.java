package com.example.mycloset.utils;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mycloset.R;

public class FragmentUtils {

    private static FragmentTransaction beginTransaction(Fragment thisFragment) {
        FragmentManager fragmentManager = thisFragment.getParentFragmentManager();
        return fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
                );
    }

    private static int commitTransaction(FragmentTransaction fragmentTransaction, Fragment thisFragment) {
        return fragmentTransaction.addToBackStack(null).commit();
    }

    public static int addFragment(Fragment thisFragment, Fragment otherFragment) {
        return beginTransaction(thisFragment)
                .add(((ViewGroup) thisFragment.getView().getParent()).getId(), otherFragment, null)
                .addToBackStack(null)
                .commit();
    }

    public static int removeFragment(Fragment thisFragment) {
        return beginTransaction(thisFragment)
                .remove(thisFragment)
                .addToBackStack(null)
                .commit();
    }

    public static int replaceFragment(Fragment thisFragment,
                               Class<? extends androidx.fragment.app.Fragment> otherFragment) {
        return beginTransaction(thisFragment)
                .replace(((ViewGroup) thisFragment.getView().getParent()).getId(), otherFragment, null)
                .addToBackStack(null)
                .commit();
    }

    public static int replaceFragment(Fragment thisFragment, Fragment otherFragment) {
        // Animations: https://developer.android.com/guide/fragments/animate
        return beginTransaction(thisFragment)
                .replace(((ViewGroup) thisFragment.getView().getParent()).getId(), otherFragment, null)
                .addToBackStack(null)
                .commit();

        //        transaction.setCustomAnimations(androidx.transition.R.anim.abc_slide_in_bottom, androidx.transition.R.anim.abc_slide_out_bottom,
//                androidx.transition.R.anim.abc_slide_in_top, androidx.transition.R.anim.abc_slide_out_top);
    }
}
