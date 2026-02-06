package com.example.blipplyprototype.ui.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfilePagerAdapter extends FragmentStateAdapter {

    public ProfilePagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new ProfileAccountFragment();
            case 1: return new ProfileCreditFragment();
            case 2: return new ProfileBusinessFragment();
            default: return new ProfileAddressesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
