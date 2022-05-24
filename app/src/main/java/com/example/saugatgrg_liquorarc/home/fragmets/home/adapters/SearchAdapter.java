package com.example.saugatgrg_liquorarc.home.fragmets.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saugatgrg_liquorarc.R;
import com.example.saugatgrg_liquorarc.api.responses.Product;
import com.example.saugatgrg_liquorarc.singleProductScreen.SingleProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    List<Product> products;
    LayoutInflater layoutInflater;
    List<Product> searchdata;
    Context context;

    public SearchAdapter(List<Product> products, Context context, boolean b){
        this.products=products;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        searchdata=new ArrayList<>(products);
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.search_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        holder.TextOf.setText(products.get(position).getName());
        Picasso.get().load(products.get(position).getImages().get(0)).into(holder.imageOf);
        holder.searchProductLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productPage =new Intent(context, SingleProductActivity.class);
                productPage.putExtra(SingleProductActivity.DATA_KEY,products.get(holder.getAdapterPosition()));
                context.startActivity(productPage);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products!=null){
            return products.size();
        }
        return 0;
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> suggestions= new ArrayList<>();
            if (constraint==null|| constraint.length()==0){
                suggestions.addAll(searchdata);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product item: searchdata){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=suggestions;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products.clear();
            products.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageOf;
        TextView TextOf;
        LinearLayout searchProductLL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOf= itemView.findViewById(R.id.imageOf);
            TextOf= itemView.findViewById(R.id.TextOf);
            searchProductLL=itemView.findViewById(R.id.searchProductLL);
        }
    }
}
