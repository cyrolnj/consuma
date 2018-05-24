package com.solutions.oryc.consuma.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    private TextView vtxtProductName;
    private TextView vtxtProductPrice;

    public ProductHolder(View itemView) {
        super(itemView);
        vtxtProductName = itemView.findViewById(R.id.vtxtProductName);
        vtxtProductPrice = itemView.findViewById(R.id.vtxtProductPrice);
    }

    public void displayProduct(Product currenProduct){
        vtxtProductName.setText(currenProduct.getName());
        vtxtProductPrice.setText(String.valueOf(currenProduct.getPrice()));
    }
}
