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

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> implements Filterable {
    List<Product> productDataList;
    List<Product> searchData;
    LayoutInflater layoutInflater;
    Context context;
    Boolean isCart = false;
    CartItemClick cartItemClick;
    Boolean removeEnabled = true;


   public ShopAdapter(List<Product> productDataList, Context context, Boolean isCart) {
        this.productDataList = productDataList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.isCart =isCart;
    }

    public void setCartItemClick(CartItemClick cartItemClick) {
        this.cartItemClick = cartItemClick;
    }

    public void setRemoveEnabled(Boolean removeEnabled) {
        this.removeEnabled = removeEnabled;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isCart)
            return new ShopViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false));
        else
            return new ShopViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.nameTV.setText(productDataList.get(position).getName());
        if(productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice()== 0){
            holder.priceTv.setVisibility(View.GONE);
            holder.discountPrice.setText("Rs. " + productDataList.get(position).getPrice()+"");
        }
        else
            holder.discountPrice.setText("Rs. " + productDataList.get(position).getDiscountPrice()+"");
        holder.priceTv.setText(productDataList.get(position).getPrice()+"");

        Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.productIV);
        holder.mainLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productPage = new Intent(context, SingleProductActivity.class);
                productPage.putExtra(SingleProductActivity.DATA_KEY, productDataList.get(holder.getAdapterPosition()));
                context.startActivity(productPage);
            }
        });

        if (isCart) {
            if (removeEnabled)
                holder.removeCartIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartItemClick.onRemoveCart(holder.getAdapterPosition());
                    }
                });
            else {

                holder.removeCartIV.setVisibility(View.GONE);
                holder.mainLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                setMargins(holder.mainLL, 0, 0, 16, 0);
            }
            holder.quantityTV.setText(productDataList.get(position).getCartQuantity() + "");
        }
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> products = new ArrayList<>();
            if (constraint==null || constraint.length()==0 ) {
                products.addAll(searchData);
            } else {
                String filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim();
                for (Product product:searchData){
                    if (product.getName().toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        products.add(product);
                    }
                    else if (product.getId().toString().toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        products.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = products;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productDataList.clear();
            productDataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV, removeCartIV;
        LinearLayout mainLL;
        TextView nameTV, priceTv, discountPrice, discountPercent, quantityTV;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            nameTV = itemView.findViewById(R.id.productNameTV);
            mainLL = itemView.findViewById(R.id.mainLL);
            priceTv = itemView.findViewById(R.id.oldPriceTV);
            discountPrice = itemView.findViewById(R.id.discountPriceTV);
            if (isCart) {
                removeCartIV = itemView.findViewById(R.id.removeCartIV);
                quantityTV = itemView.findViewById(R.id.quantityTV);

            }

        }
    }


    public interface CartItemClick {
        public void onRemoveCart(int position);
    }
}
