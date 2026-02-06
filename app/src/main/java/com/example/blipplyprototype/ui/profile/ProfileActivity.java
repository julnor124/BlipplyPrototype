package com.example.blipplyprototype.ui.profile;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.blipplyprototype.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton back = findViewById(R.id.buttonBack);
        TabLayout tabs = findViewById(R.id.tabLayout);
        ViewPager2 pager = findViewById(R.id.viewPager);

        back.setOnClickListener(v -> finish());

        ProfilePagerAdapter adapter = new ProfilePagerAdapter(this);
        pager.setAdapter(adapter);

        new TabLayoutMediator(tabs, pager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Account"); break;
                case 1: tab.setText("Credit"); break;
                case 2: tab.setText("Business"); break;
                case 3: tab.setText("Addresses"); break;
            }
        }).attach();
    }
}
