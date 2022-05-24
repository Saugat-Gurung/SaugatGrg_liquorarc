package com.example.saugatgrg_liquorarc.checkout.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.api.ApiClient;
import com.example.saugatgrg_liquorarc.api.responses.Adress;
import com.example.saugatgrg_liquorarc.api.responses.AddressResponse;
import com.example.saugatgrg_liquorarc.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    RecyclerView addressRV;
    public static String ADDRESS_SELECTED_KEY = "DFa";
    AddressAdapter addressAdapter;
    List<Adress> data;
    ImageView backIVaddress;
    TextView addaddressTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_address);
        addressRV = findViewById(R.id.addressRV);
        addaddressTV = findViewById(R.id.addaddressTV);
        backIVaddress = findViewById(R.id.backIVaddress);

        backIVaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addaddressTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddAddressActivity.class);
                startActivity(intent);
            }
        });
        getAddressOnline();
    }

    private void getAddressOnline() {
        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<AddressResponse> addressResponseCall = ApiClient.getClient().getMyAddresses(key);
        addressResponseCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()) {
                    listAddress(response.body().getAdresses());
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });
    }

    private void listAddress(List<Adress> adresses) {
        data=adresses;
        addressRV.setHasFixedSize(true);
        addressRV.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new AddressAdapter(adresses, this);
        addressAdapter.setOnAddressItemClickListener(new AddressAdapter.OnAddressItemClickListener() {
            @Override
            public void onAddressClick(int position, Adress address) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(ADDRESS_SELECTED_KEY, address);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        addressRV.setAdapter(addressAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressOnline();
    }
}