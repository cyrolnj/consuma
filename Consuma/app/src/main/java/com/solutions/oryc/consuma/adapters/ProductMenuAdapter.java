package com.solutions.oryc.consuma.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.holders.ProductMenuHolder;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenuAdapter extends RecyclerView.Adapter {

    public ArrayList<ProductMenu> menuList;
    private ProductMenuHolder menuHolder;

    public ProductMenuAdapter(ArrayList<ProductMenu> newMenuList){
        this.menuList = newMenuList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View elementoPrincipalXml = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        menuHolder = new ProductMenuHolder(elementoPrincipalXml);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        menuHolder = (ProductMenuHolder) holder;
        ProductMenu currentMenu = this.menuList.get(position);
        menuHolder.displayMenu( currentMenu );
    }

    @Override
    public int getItemCount() {
        return this.menuList.size();
    }
}
