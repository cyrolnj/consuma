package com.solutions.oryc.consuma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.Interfaces.ProductOnItemClickListener;
import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.control.ShoppingCart;
import com.solutions.oryc.consuma.model.ProductMenuDao;

import java.util.ArrayList;

public class ReadMenuActivity extends AppCompatActivity {

    public final int BACK_PRESSED = 8;
    public final int OPEN_CART = 200;
    public final int READ_ACTIVITY = 2;
    private TextView vtxtMenuName;
    private ProductMenu productMenu = new ProductMenu();
    private DatabaseReference dbRef;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private ProductAdapter productAdapter = new ProductAdapter( productList, READ_ACTIVITY );
    private ShoppingCart shoppingCart;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

                //Define shoppingCart attributes
                shoppingCart = new ShoppingCart();
                shoppingCart.setBuyerUserId(user.getUid());
                shoppingCart.setSellerUserId(productMenu.getUserId());

                vtxtMenuName = findViewById(R.id.vtxtMenuName);
                vtxtMenuName.setText(productMenu.getName());

                ArrayList<Product> localProductList = productMenu.getProductList();
                productList.clear();
                for (Product product : localProductList) {
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

        productAdapter.setOnItemClickListener(new ProductOnItemClickListener() {
            @Override
            public void onEditClick(int position) {
            }

            @Override
            public void onDeleteClick(int position) {
            }

            @Override
            public void onAddToCartClick(int position) {
                Product product = productList.get(position);
                product.setQuantity(1);
                shoppingCart.addProduct(product);
                Toast.makeText(ReadMenuActivity.this, "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onIncreaseQuantityClick(int position) {

            }

            @Override
            public void onDecreaseQuantityClick(int position) {

            }
        });
    }

    public void onClickShoppingCart(View view){
        Intent ShoppingCartIntent = new Intent(this, ShoppingCartActivity.class);
        ShoppingCartIntent.putExtra("shoppingCart", shoppingCart);
        startActivityForResult(ShoppingCartIntent, OPEN_CART);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case (OPEN_CART) : {
                if (resultCode == BACK_PRESSED) {
                    //Extract the data returned from the child Activity.
                    shoppingCart = (ShoppingCart) data.getParcelableExtra("shoppingCart");
                }
                break;
            }
            case (100) : {
                if (resultCode == 100) {
                    //Extract the data returned from the child Activity.
                    //Product product = (Product) data.getParcelableExtra("product");
                    //position = (int) data.getIntExtra("position", 0);
                    //productMenu.updateProduct(product, position);
                }
                break;
            }
        }

        productAdapter.notifyDataSetChanged();
    }
}
