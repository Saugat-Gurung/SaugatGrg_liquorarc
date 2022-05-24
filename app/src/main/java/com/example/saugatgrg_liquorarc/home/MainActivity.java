package com.example.saugatgrg_liquorarc.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.home.fragmets.CartFragment;
import com.example.saugatgrg_liquorarc.home.fragmets.CategoryFragment;
import com.example.saugatgrg_liquorarc.home.fragmets.ProfileFragmnet;
import com.example.saugatgrg_liquorarc.home.fragmets.home.HomeFragment;
import com.example.saugatgrg_liquorarc.utils.UserInterfaceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    CartFragment cartFragment;
    ProfileFragmnet profileFragmnet;
    CategoryFragment categoryFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.homeBottomNav);
        homeFragment = new HomeFragment();
        homeFragment.setBottomNavigationView(bottomNavigationView);
        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.homeFrameContainer, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getTitle().equals(getString(R.string.home))) {
                    if (homeFragment == null)
                        homeFragment = new HomeFragment();
                    changeFragment(homeFragment);
                    return true;
                } else if (item.getTitle().equals(getString(R.string.categories))) {

                    if (categoryFragment == null)
                        categoryFragment = new CategoryFragment();
                    changeFragment(categoryFragment);
                    return true;

                }else if (item.getTitle().equals(getString(R.string.cart))) {

                    if (cartFragment == null)
                        cartFragment = new CartFragment();
                    changeFragment(cartFragment);
                    return true;

                } else if (item.getTitle().equals(getString(R.string.profile))) {
                    if (profileFragmnet == null)
                        profileFragmnet = new ProfileFragmnet();
                    changeFragment(profileFragmnet);
                    return true;
                }
                return false;

            }
        });


    }

    public  void  onSearchClicked(View v){
        Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
    }
    void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        if (fragment == profileFragmnet)
            UserInterfaceUtils.changeStatusBarColor(this,true);
        else
            UserInterfaceUtils.changeStatusBarColor(this, false);

        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.homeFrameContainer, fragment).commit();
        }
        currentFragment = fragment;
    }
}