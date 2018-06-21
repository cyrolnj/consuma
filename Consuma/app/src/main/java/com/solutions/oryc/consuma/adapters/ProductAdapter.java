package com.solutions.oryc.consuma.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.solutions.oryc.consuma.Interfaces.ProductOnItemClickListener;
import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.holders.ProductHolder;
import com.solutions.oryc.consuma.holders.ProductMenuHolder;
import com.solutions.oryc.consuma.model.ProductMenuDao;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter {

    public ArrayList<Product> productList;
    private ProductHolder productHolder;
    private ProductOnItemClickListener onItemClickListener;
    private int activity;



    public ProductAdapter(ArrayList<Product> productList, int activity){
        this.productList = productList;
        this.activity = activity;
    }

    public void setOnItemClickListener(ProductOnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        productHolder = new ProductHolder(inflatedView, onItemClickListener, activity);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        productHolder = (ProductHolder) holder;
        Product currentProduct = this.productList.get(position);
        productHolder.displayProduct( currentProduct );
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }


}
