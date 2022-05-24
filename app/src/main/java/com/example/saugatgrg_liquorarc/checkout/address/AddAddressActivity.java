package com.example.saugatgrg_liquorarc.checkout.address;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.api.ApiClient;
import com.example.saugatgrg_liquorarc.api.responses.AddressResponse;
import com.example.saugatgrg_liquorarc.api.responses.Adress;
import com.example.saugatgrg_liquorarc.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {

    EditText City, Street, desc, province;
    static String ADDED_KEY = "ad";
    static String ADDED_DATA_KEY = "adk";
    ImageView backIV;
    Button addbtn, cancel;
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        City = findViewById(R.id.City);
        Street = findViewById(R.id.Street);
        desc = findViewById(R.id.desc);
        province = findViewById(R.id.province);
        addbtn = findViewById(R.id.addbtn);
        cancel = findViewById(R.id.cancel);
        backIV = findViewById(R.id.backIV2);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setOnClickListener();
    }

    private void setOnClickListener() {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String key = SharedPrefUtils.getString(AddAddressActivity.this, getString(R.string.api_key));
                    Call<AddressResponse> addressResponseCall = ApiClient.getClient().addAddress(key, City.getText().toString(), Street.getText().toString(), province.getText().toString(), desc.getText().toString());

                    addressResponseCall.enqueue(new Callback<AddressResponse>() {
                        @Override
                        public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                            AddressResponse registerResponse = response.body();
                            if (response.isSuccessful()){
                                if (!registerResponse.getError()) {
                                    Adress address = new Adress();
                                    address.setCity(City.getText().toString());
                                    address.setStreet(Street.getText().toString());
                                    address.setProvince(province.getText().toString());
                                    address.setDescription(desc.getText().toString());
                                    address.setId(address.getId());
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(ADDED_KEY, true);
                                    resultIntent.putExtra(ADDED_DATA_KEY, address);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddressResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }




    private boolean validate() {

        boolean validate = true;
        if (City.getText().toString().isEmpty()
                || Street.getText().toString().isEmpty()
                || province.getText().toString().isEmpty()
                || desc.getText().toString().isEmpty()) {
            Toast.makeText(AddAddressActivity.this, "None of the above fields can be empty", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }
}