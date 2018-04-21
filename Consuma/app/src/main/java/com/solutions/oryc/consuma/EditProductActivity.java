package com.solutions.oryc.consuma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class EditProductActivity extends AppCompatActivity {

    private EditText name;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
    }

    public void onClickSaveProduct(View view){
        Product product = new Product();

        name = findViewById(R.id.txtProductName);
        price = findViewById(R.id.txtProductPrice);

        product.setName(name.getText().toString());
        product.setPrice(Float.parseFloat(price.getText().toString()));

        ProductMenuDao.addProductToCurrentMenu(product);
        finish();
    }
}
