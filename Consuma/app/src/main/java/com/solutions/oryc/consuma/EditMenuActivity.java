package com.solutions.oryc.consuma;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.solutions.oryc.consuma.Interfaces.ProductOnItemClickListener;
import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class EditMenuActivity extends AppCompatActivity {

    public final int EDIT_ACTIVITY = 1;
    public final int NEW_PRODUCT = 1;
    public final int EDIT_PRODUCT = 2;
    public final int PRODUCT_SAVED = 1;
    private ProductAdapter productAdapter;
    private EditText menuName;
    private ProductMenu productMenu;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        productMenu = (ProductMenu) getIntent().getParcelableExtra("productMenu");
        if (productMenu == null) {
            productMenu = new ProductMenu();
            productMenu.setId("");
        }

        menuName = findViewById(R.id.txtMenuName);
        menuName.setText(productMenu.getName());

        RecyclerView rvProductList = findViewById(R.id.rvProductList);
        productAdapter = new ProductAdapter( productMenu.getProductList(), EDIT_ACTIVITY);
        rvProductList.setAdapter(productAdapter);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));

        productAdapter.setOnItemClickListener(new ProductOnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Intent editProductIntent = new Intent(EditMenuActivity.this, EditProductActivity.class);

                Product product =  productMenu.productList.get(position);
                editProductIntent.putExtra("product", product);
                editProductIntent.putExtra("position", position);
                startActivityForResult(editProductIntent, EDIT_PRODUCT);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onAddToCartClick(int position) {
            }

            @Override
            public void onIncreaseQuantityClick(int position) {

            }

            @Override
            public void onDecreaseQuantityClick(int position) {

            }
        });

    }

    private void removeItem(int position) {
        productMenu.getProductList().remove(position);
        productAdapter.notifyItemRemoved(position);
    }

    public void onClickNewProduct(View view){
        Intent editProductIntent = new Intent(this, EditProductActivity.class);
        startActivityForResult(editProductIntent, NEW_PRODUCT);
    }

    public void onClickSaveMenu(View view){
        menuName = findViewById(R.id.txtMenuName);
        productMenu.setName(menuName.getText().toString());
        productMenu.setUserId(user.getUid());

        if (productMenu.getId().isEmpty()) {
            ProductMenuDao.createProductMenu(productMenu);
        } else {
            ProductMenuDao.updateProductMenu(productMenu);
        }

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case (NEW_PRODUCT) : {
                if (resultCode == PRODUCT_SAVED) {
                    //Extract the data returned from the child Activity.
                    Product product = (Product) data.getParcelableExtra("product");
                    productMenu.addProduct(product);
                }
                break;
            }
            case (EDIT_PRODUCT) : {
                if (resultCode == PRODUCT_SAVED) {
                    //Extract the data returned from the child Activity.
                    Product product = (Product) data.getParcelableExtra("product");
                    int position = (int) data.getIntExtra("position", 0);
                    productMenu.updateProduct(product, position);
                }
                break;
            }
        }

        productAdapter.notifyDataSetChanged();
    }
}
