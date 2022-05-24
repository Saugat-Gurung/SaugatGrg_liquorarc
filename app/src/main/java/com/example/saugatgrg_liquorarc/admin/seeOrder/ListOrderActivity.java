package com.example.saugatgrg_liquorarc.admin.seeOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.saugatgrg_liquorarc.R;

public class ListOrderActivity extends AppCompatActivity {

    RecyclerView fullOrdRV;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);
        fullOrdRV = findViewById(R.id.fullOrdRV);
        getOnline();
    }

    private void getOnline() {
    }
}