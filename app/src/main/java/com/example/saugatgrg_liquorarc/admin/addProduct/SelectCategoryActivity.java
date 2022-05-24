package com.example.saugatgrg_liquorarc.admin.addProduct;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.api.ApiClient;
import com.example.saugatgrg_liquorarc.api.responses.Category;
import com.example.saugatgrg_liquorarc.api.responses.CategoryResponse;
import com.example.saugatgrg_liquorarc.home.fragmets.home.adapters.CategoryAdapter;
import com.example.saugatgrg_liquorarc.utils.DataHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCategoryActivity extends AppCompatActivity {
    RecyclerView fullCatRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("All Categories");
        setContentView(R.layout.activity_add_category);
        fullCatRV = findViewById(R.id.fullCatRV);
        getOnline();
    }

    private void getOnline() {
        Call<CategoryResponse> getCategories = ApiClient.getClient().getCategories();
        getCategories.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        DataHolder.categories = response.body().getCategories();
                        showCategoryView(response.body().getCategories());

                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
//                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void showCategoryView(List<Category> categories) {
        fullCatRV.setHasFixedSize(true);
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this, false);
        fullCatRV.setLayoutManager(new GridLayoutManager(this, 1));
        fullCatRV.setAdapter(categoryAdapter);
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
}

