package com.solutions.oryc.consuma;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class EditMenuActivity extends AppCompatActivity {

    public final int EDIT_PRODUCT = 1;
    public final int PRODUCT_SAVED = 1;
    private ProductAdapter productAdapter;
    private EditText menuName;
    private final ProductMenu productMenu = new ProductMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        //Button btnAddToCart;
        //btnAddToCart = findViewById(R.id.btnAddToCart);
        //btnAddToCart.setVisibility(View.GONE);

        RecyclerView rvProductList = findViewById(R.id.rvProductList);
        productAdapter = new ProductAdapter( productMenu.getProductList() );
        rvProductList.setAdapter(productAdapter);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onClickNewProduct(View view){
        Intent editProductIntent = new Intent(this, EditProductActivity.class);
        menuName = findViewById(R.id.txtMenuName);
        productMenu.setName(menuName.getText().toString());
        editProductIntent.putExtra("productMenu", productMenu);
        startActivityForResult(editProductIntent, EDIT_PRODUCT);
    }

    public void onClickSaveMenu(View view){
        menuName = findViewById(R.id.txtMenuName);
        productMenu.setName(menuName.getText().toString());
        ProductMenuDao.createProductMenu(productMenu);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case (EDIT_PRODUCT) : {
                if (resultCode == PRODUCT_SAVED) {
                    //Extract the data returned from the child Activity.
                    Product product = (Product) data.getParcelableExtra("product");
                    productMenu.addProduct(product);
                }
                break;
            }
        }

        productAdapter.notifyDataSetChanged();
    }
}
