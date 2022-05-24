package com.example.saugatgrg_liquorarc.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.home.MainActivity;
import com.example.saugatgrg_liquorarc.userAccount.UserAccountActivity;
import com.example.saugatgrg_liquorarc.utils.SharedPrefUtils;


public class SplashScreenActivity extends AppCompatActivity {
    boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogged = SharedPrefUtils.getBool(SplashScreenActivity.this, getString(R.string.isLogged), false);
                System.out.println(isLogged);
                if (isLogged)
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashScreenActivity.this, UserAccountActivity.class));
                finish();
            }
        }, 400);


    }


}