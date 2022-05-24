
package com.example.saugatgrg_liquorarc.userAccount.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.admin.AdminActivity;
import com.example.saugatgrg_liquorarc.api.ApiClient;
import com.example.saugatgrg_liquorarc.api.responses.LoginResponse;
import com.example.saugatgrg_liquorarc.home.MainActivity;
import com.example.saugatgrg_liquorarc.utils.SharedPrefUtils;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText emailEt, passwordET;
    LinearLayout loginBtn;
    boolean visibilityPW;
    ProgressBar circularProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(Color.WHITE);
        emailEt = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        loginBtn = view.findViewById(R.id.loginLL);
        circularProgress = view.findViewById(R.id.circularProgress);
        loginBtn.setOnClickListener(this);

        passwordET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX() > passwordET.getRight()- passwordET.getCompoundDrawables()[Right].getBounds().width()){
                        int select = passwordET.getSelectionEnd();
                        if (visibilityPW){
                            //set drawable image here
                            passwordET.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_off_24,0);
                            //for hiding password
                            passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            visibilityPW = false;
                        }else {
                            //set drawable image here
                            passwordET.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_24,0);
                            //for showing password
                            passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            visibilityPW = true;
                        }
                        passwordET.setSelection(select);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public void toggleProgress(Boolean toogle){
        if(toogle)
            circularProgress.setVisibility(View.VISIBLE);
        else
            circularProgress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        if (v == loginBtn) {

            String email, password;
            email = emailEt.getText().toString();
            password = passwordET.getText().toString();
            if (email.isEmpty() && password.isEmpty())
                Toast.makeText(getContext(), "Email or Password is Empty!", Toast.LENGTH_LONG).show();
            else {
                toggleProgress(true);
                System.out.println("22222222222222 Email is: " + email);
                System.out.println("22222222222222 Password is: " + password);

                Call<LoginResponse> login = ApiClient.getClient().login(email, password);
                login.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        toggleProgress(false);
                        LoginResponse loginResponse = response.body();
                        if (response.isSuccessful()) {
                            if (loginResponse.getError()) {
                                System.out.println("222222221222222222222 my error  is: " + loginResponse.getError());
                                Toast.makeText(getActivity(), "Login failed. Incorrect credentials", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                                SharedPrefUtils.setBoolean(getActivity(), getString(R.string.isLogged), true);
                                SharedPrefUtils.setString(getActivity(), getString(R.string.name_key), loginResponse.getName());
                                SharedPrefUtils.setString(getActivity(), getString(R.string.email_id), loginResponse.getEmail());
                                SharedPrefUtils.setString(getActivity(), getString(R.string.created_key), loginResponse.getCreatedAt());
                                SharedPrefUtils.setString(getActivity(),  getString(R.string.api_key), loginResponse.getApiKey());
                                SharedPrefUtils.setBoolean(getActivity(),  getString(R.string.staff_key), loginResponse.getIsStaff());
//                                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
//                                getActivity().finish();



                                getActivity().startActivity(new Intent(getContext(),loginResponse.getIsStaff() ? AdminActivity.class :MainActivity.class));
                                getActivity().finish();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        toggleProgress(false);

                    }
                });

            }



//            else if (email.equals("thesaugatt@gmail.com") && password.equals("Pass123")) {
//                Toast.makeText(getContext(), "Welcome", Toast.LENGTH_LONG).show();
//                SharedPrefUtils.setBoolean(getActivity(), getString(R.string.isLogged), true);
//                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
//                getActivity().finish();
//
//
//            } else
//                Toast.makeText(getContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
        }
    }
}