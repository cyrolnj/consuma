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
    //private Intent resultIntent = new Intent();
    private ProductMenu menu;
    private EditText name;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        menu = (ProductMenu) getIntent().getParcelableExtra("productMenu");
        Toast.makeText(EditProductActivity.this, "Adicionando item ao cardápio: " + menu.getName(), Toast.LENGTH_LONG).show();
    }

    public void onClickSaveProduct(View view){
        Product product = new Product();
        name = findViewById(R.id.txtProductName);
        price = findViewById(R.id.txtProductPrice);
        product.setName(name.getText().toString());
        product.setPrice(Float.parseFloat(price.getText().toString()));
        Intent resultIntent = new Intent();
        resultIntent.putExtra("product", product);
        setResult(PRODUCT_SAVED, resultIntent);
        finish();
    }
}
