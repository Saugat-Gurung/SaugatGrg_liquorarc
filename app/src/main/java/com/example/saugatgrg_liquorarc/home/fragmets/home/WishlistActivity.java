package com.example.saugatgrg_liquorarc.home.fragmets.home;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.api.ApiClient;
import com.example.saugatgrg_liquorarc.api.responses.AllProductResponse;
import com.example.saugatgrg_liquorarc.api.responses.Product;
import com.example.saugatgrg_liquorarc.api.responses.RegisterResponse;
import com.example.saugatgrg_liquorarc.home.fragmets.home.adapters.WishlistAdapter;
import com.example.saugatgrg_liquorarc.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {

    ImageView backabt;
    RecyclerView allWishlistProductRV;
    List<Product> products;
    SwipeRefreshLayout swipeRefresh;
    ImageView emptyWishlistIV;
    AllProductResponse allProductResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_wishlist);

        allWishlistProductRV = findViewById(R.id.allWishlistProductRV);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        emptyWishlistIV = findViewById(R.id.emptyWishlistIV);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                getWishlistItems();
            }
        });
        getWishlistItems();
        backabt = findViewById(R.id.backABt);

        backabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getWishlistItems() {
        //load
        String key = SharedPrefUtils.getString(this, "apk");
        Call<AllProductResponse> wishItemsCall = ApiClient.getClient().getMyWishlist(key);
        wishItemsCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        if (response.body().getProducts().size() == 0) {
                            showEmptyLayout();
                        } else {
                            emptyWishlistIV.setVisibility(View.GONE);
                            allProductResponse = response.body();
                            products = response.body().getProducts();
                            loadWishList();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void loadWishList() {
        allWishlistProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allWishlistProductRV.setLayoutManager(layoutManager);
        WishlistAdapter wishlistAdapter = new WishlistAdapter(products, this);
        wishlistAdapter.setWishCartItemClick(new WishlistAdapter.WishlistCartItemClick() {
            @Override
            public void onRemoveWishlist(int position) {
                String key = SharedPrefUtils.getString(WishlistActivity.this, "apk");
                Call<RegisterResponse> deleteFromWishlistCall = ApiClient.getClient().deleteFromWishlist(key, products.get(position).getWishlistId());
                deleteFromWishlistCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                products.remove(products.get(position));
                                wishlistAdapter.notifyItemChanged(position);
                                Toast.makeText(getApplicationContext(), "Wishlist Item successfully deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });

            }
        });
        allWishlistProductRV.setAdapter(wishlistAdapter);
    }

    private void showEmptyLayout() {
        emptyWishlistIV.setVisibility(View.VISIBLE);
    }


}