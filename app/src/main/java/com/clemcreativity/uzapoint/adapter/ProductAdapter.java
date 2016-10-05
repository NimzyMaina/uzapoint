package com.clemcreativity.uzapoint.adapter;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clemcreativity.uzapoint.R;
import com.clemcreativity.uzapoint.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/8/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<Product> productsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name, code, quantity;

    public MyViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        code = (TextView) view.findViewById(R.id.code);
        quantity = (TextView) view.findViewById(R.id.quantity);
    }
}


    public ProductAdapter(List<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productsList.get(position);
        holder.name.setText(product.getName());
        holder.code.setText(product.getCode());
        holder.quantity.setText(product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void setFilter(List<Product> products) {
        productsList = new ArrayList<>();
        productsList.addAll(products);
        notifyDataSetChanged();
    }
}