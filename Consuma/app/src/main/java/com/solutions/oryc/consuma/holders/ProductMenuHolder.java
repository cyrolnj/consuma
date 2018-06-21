package com.solutions.oryc.consuma.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.oryc.consuma.EditMenuActivity;
import com.solutions.oryc.consuma.R;
import com.solutions.oryc.consuma.ShowProductMenuQrCodeActivity;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenuHolder extends RecyclerView.ViewHolder {

    private TextView vtxtMenuName;
    private Button btnDelete;
    private Button btnEdit;
    private Button btnGenerateQrCode;
    private final Context context;

    public ProductMenuHolder(View itemView) {
        super(itemView);
        vtxtMenuName = itemView.findViewById(R.id.vtxtMenuName);
        context = itemView.getContext();
    }

    public void displayMenu(ProductMenu currentMenu){
        final ProductMenu finalCurrentMenu = currentMenu;
        final Intent editMenuIntent = new Intent(this.context, EditMenuActivity.class);
        final Intent showQrCode = new Intent(this.context, ShowProductMenuQrCodeActivity.class);

        vtxtMenuName.setText(currentMenu.getName());
        btnDelete = itemView.findViewById(R.id.btnDeleteMenu);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductMenuDao.deleteProductMenu(finalCurrentMenu);
            }
        });

        btnEdit = itemView.findViewById(R.id.btnEditMenu);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMenuIntent.putExtra("productMenu", finalCurrentMenu);
                context.startActivity(editMenuIntent);
            }
        });

        btnGenerateQrCode = itemView.findViewById(R.id.btnGenerateQrCode);
        btnGenerateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQrCode.putExtra("productMenu", finalCurrentMenu);
                context.startActivity(showQrCode);
            }
        });

    }

}
