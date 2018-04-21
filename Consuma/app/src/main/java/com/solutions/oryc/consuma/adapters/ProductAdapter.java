package com.solutions.oryc.consuma.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.holders.ProductHolder;
import com.solutions.oryc.consuma.holders.ProductMenuHolder;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter {

    public ArrayList<Product> productList;
    private ProductHolder productHolder;

    public ProductAdapter(ArrayList<Product> newProductList){
        this.productList = newProductList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View elementoPrincipalXml = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        productHolder = new ProductHolder(elementoPrincipalXml);
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
