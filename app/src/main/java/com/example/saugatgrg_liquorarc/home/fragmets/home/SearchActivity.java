package com.example.saugatgrg_liquorarc.home.fragmets.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.api.ApiClient;
import com.example.saugatgrg_liquorarc.api.responses.AllProductResponse;
import com.example.saugatgrg_liquorarc.api.responses.Product;
import com.example.saugatgrg_liquorarc.home.fragmets.home.adapters.SearchAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    android.widget.SearchView searchView;
    RecyclerView food_RV;
    SearchAdapter searchAdapter;
    //    ShopAdapter shopAdapter;
    LinearLayout searchLL;
    ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchView);
        food_RV = findViewById(R.id.food_RV);
        searchLL = findViewById(R.id.searchLL);

        searchListener();

        Call<AllProductResponse> allProductResponseCall= ApiClient.getClient().getAllProducts();
        allProductResponseCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getError()){
                        setSearchView(response.body().getProducts());
                    }
                }
            }

            private void setSearchView(List<Product> products) {
                food_RV.setHasFixedSize(true);
                food_RV.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));
//                shopAdapter = new ShopAdapter(cycles, SearchActivity.this, false);
//                food_RV.setAdapter(shopAdapter);
                searchAdapter = new SearchAdapter(products, SearchActivity.this, false);
                food_RV.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {

            }
        });
    }

    private void searchListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (searchAdapter!=null){
                    searchAdapter.getFilter().filter(newText);
                }


                return false;
            }
        });
    }
}