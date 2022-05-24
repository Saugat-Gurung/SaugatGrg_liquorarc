package com.example.saugatgrg_liquorarc.home.fragmets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.admin.AdminActivity;
import com.example.saugatgrg_liquorarc.checkout.address.AddressActivity;
import com.example.saugatgrg_liquorarc.checkout.orderComplete.OrderActivity;
import com.example.saugatgrg_liquorarc.home.AboutUsActivity;
import com.example.saugatgrg_liquorarc.home.fragmets.home.WishlistActivity;
import com.example.saugatgrg_liquorarc.userAccount.UserAccountActivity;
import com.example.saugatgrg_liquorarc.utils.SharedPrefUtils;


public class ProfileFragmnet extends Fragment {

    TextView logOutTV, adminAreaTV, addressTV, ordersTV, aboutus, wishlistTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile_fragmnet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutTV = view.findViewById(R.id.logOutTV);
        adminAreaTV = view.findViewById(R.id.adminAreaTV);
        addressTV = view.findViewById(R.id.addressTV);
        ordersTV = view.findViewById(R.id.ordersTV);
        aboutus = view.findViewById(R.id.aboutus);
        wishlistTV = view.findViewById(R.id.wishListTV);
//        checkAdmin();
        setClickListeners();
    }

//    private void checkAdmin() {
//        boolean is_staff = SharedPrefUtils.getBool(getActivity(), getString(R.string.staff_key), false);
//        if (is_staff)
//            adminAreaTV.setVisibility(View.VISIBLE);
//    }

    private void setClickListeners() {
        logOutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefUtils.clear(getContext());
                Intent userAccount = new Intent(getContext(), UserAccountActivity.class);
                startActivity(userAccount);
                getActivity().finish();
            }
        });

        adminAreaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
            }
        });
        addressTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });

        wishlistTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });


        ordersTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
    }
}