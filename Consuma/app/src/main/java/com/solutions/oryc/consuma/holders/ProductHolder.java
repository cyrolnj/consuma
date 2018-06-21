package com.solutions.oryc.consuma.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.solutions.oryc.consuma.EditMenuActivity;
import com.solutions.oryc.consuma.EditProductActivity;
import com.solutions.oryc.consuma.Interfaces.ProductOnItemClickListener;
import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    private final int EDIT_ACTIVITY = 1;
    private final int READ_ACTIVITY = 2;
    private final int SHOP_ACTIVITY = 3;
    private final int READ_TRANSACTION_ACTIVITY = 4;

    private TextView vtxtProductName;
    private TextView vtxtProductPrice;
    private TextView vtxtProductQuantity;
    private TextView vtxtQuantityLable;
    private Button btnDelete;
    private Button btnEdit;
    private Button btnAddToCart;
    private Button btnIncreaseQuantity;
    private Button btnDecreaseQuantity;

    public ProductHolder(View itemView, final ProductOnItemClickListener listener, int activity) {
        super(itemView);

        vtxtProductName = itemView.findViewById(R.id.vtxtProductName);
        vtxtProductPrice = itemView.findViewById(R.id.vtxtProductPrice);
        vtxtProductQuantity = itemView.findViewById(R.id.vtxtProductQuantity);
        vtxtQuantityLable = itemView.findViewById(R.id.vtxtQuantityLable);

        btnEdit = itemView.findViewById(R.id.btnEditProduct);
        btnDelete = itemView.findViewById(R.id.btnDeleteProduct);
        btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        btnIncreaseQuantity = itemView.findViewById(R.id.btnIncreaseQuantity);
        btnDecreaseQuantity = itemView.findViewById(R.id.btnDecreaseQuantity);

        switch (activity) {
            case EDIT_ACTIVITY:
                btnAddToCart.setVisibility(View.GONE);
                btnIncreaseQuantity.setVisibility(View.GONE);
                btnDecreaseQuantity.setVisibility(View.GONE);
                vtxtProductQuantity.setVisibility(View.GONE);
                vtxtQuantityLable.setVisibility(View.GONE);
                break;
            case READ_ACTIVITY:
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnIncreaseQuantity.setVisibility(View.GONE);
                btnDecreaseQuantity.setVisibility(View.GONE);
                vtxtProductQuantity.setVisibility(View.GONE);
                vtxtQuantityLable.setVisibility(View.GONE);
                break;
            case SHOP_ACTIVITY:
                btnEdit.setVisibility(View.GONE);
                btnAddToCart.setVisibility(View.GONE);
                vtxtQuantityLable.setVisibility(View.GONE);
                break;
            case READ_TRANSACTION_ACTIVITY:
                btnEdit.setVisibility(View.GONE);
                btnAddToCart.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnIncreaseQuantity.setVisibility(View.GONE);
                btnDecreaseQuantity.setVisibility(View.GONE);
                break;
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onAddToCartClick(position);
                    }
                }
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onIncreaseQuantityClick(position);
                    }
                }
            }
        });

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDecreaseQuantityClick(position);
                    }
                }
            }
        });

    }

    public void displayProduct(Product currentProduct){
        vtxtProductName.setText(currentProduct.getName());
        vtxtProductPrice.setText(String.valueOf(currentProduct.getPrice()));
        vtxtProductQuantity.setText(String.valueOf(currentProduct.getQuantity()));
    }

}
