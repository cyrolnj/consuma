package com.solutions.oryc.consuma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class EditProductActivity extends AppCompatActivity {

    public final int PRODUCT_SAVED = 1;
    //private ProductMenu productMenu;
    private Product product;
    private int position;
    private EditText name;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        name = findViewById(R.id.txtProductName);
        price = findViewById(R.id.txtProductPrice);

        position = (int) getIntent().getIntExtra("position", 0);
        product = (Product) getIntent().getParcelableExtra("product");
        if (product == null) {
            product = new Product();
        }

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));

        //productMenu = (ProductMenu) getIntent().getParcelableExtra("productMenu");

    }

    public void onClickSaveProduct(View view){
        product.setName(name.getText().toString());
        product.setPrice(Float.parseFloat(price.getText().toString()));
        Intent resultIntent = new Intent();
        resultIntent.putExtra("product", product);
        resultIntent.putExtra("position", position);
        setResult(PRODUCT_SAVED, resultIntent);
        finish();
    }
}
