package com.solutions.oryc.consuma;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class EditMenuActivity extends AppCompatActivity {

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        RecyclerView rvProductList = findViewById(R.id.rvProductList);
        ProductMenu newProductMenu = ProductMenuDao.getCurrentMenu();

        productAdapter = new ProductAdapter( newProductMenu.getProductList() );
        rvProductList.setAdapter(productAdapter);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onClickNewProduct(View view){
        Intent editProductIntent = new Intent(this, EditProductActivity.class);
        startActivityForResult(editProductIntent, 200);
    }

    public void onClickSaveMenu(View view){
        EditText menuName = findViewById(R.id.txtMenuName);
        ProductMenuDao.setCurrentMenuName(menuName.getText().toString());
        ProductMenu productMenu = ProductMenuDao.getCurrentMenu();
        ProductMenuDao.addMenu(productMenu);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        productAdapter.notifyDataSetChanged();
    }
}
