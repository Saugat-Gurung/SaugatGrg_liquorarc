package com.example.saugatgrg_liquorarc.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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