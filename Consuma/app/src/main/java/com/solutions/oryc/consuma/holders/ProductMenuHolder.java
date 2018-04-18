package com.solutions.oryc.consuma.holders;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.control.ProductMenu;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenuHolder extends RecyclerView.ViewHolder {

    private TextView vtxtMenuName;

    public ProductMenuHolder(View itemView) {
        super(itemView);
        vtxtMenuName = itemView.findViewById(R.id.vtxtMenuName);
    }

    public void displayMenu(ProductMenu currentMenu){
        vtxtMenuName.setText(currentMenu.getName());
    }
}
