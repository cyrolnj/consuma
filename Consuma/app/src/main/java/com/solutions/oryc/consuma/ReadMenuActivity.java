package com.solutions.oryc.consuma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

import java.util.ArrayList;

public class ReadMenuActivity extends AppCompatActivity {

    private TextView vtxtMenuName;
    private ProductMenu productMenu = new ProductMenu();
    private DatabaseReference dbRef;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private ProductAdapter productAdapter = new ProductAdapter( productList );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_menu);

        String productMenuId = getIntent().getStringExtra("productMenuId");

        dbRef = FirebaseDatabase.getInstance().getReference("productMenu/" + productMenuId);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                productMenu = dataSnapshot.getValue(ProductMenu.class);

                vtxtMenuName = findViewById(R.id.vtxtMenuName);
                vtxtMenuName.setText(productMenu.getName());

                ArrayList<Product> localProctList = productMenu.getProductList();
                for (Product product : localProctList) {
                    productList.add(product);
                }

                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView rvProductList = findViewById(R.id.rvProductList);
        rvProductList.setAdapter(productAdapter);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClickShoppingCart(View view){

    }
}
