package com.solutions.oryc.consuma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.model.ProductMenuDao;

import java.util.ArrayList;

public class MenuListActivity extends AppCompatActivity {

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("productMenu");
    private ArrayList<ProductMenu> productMenuList = new ArrayList<ProductMenu>();
    private ProductMenuAdapter menuAdapter = new ProductMenuAdapter( productMenuList );
    private RecyclerView rvMenuList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                productMenuList.clear();
                for (DataSnapshot child : children ) {
                    ProductMenu currentProductMenu = child.getValue(ProductMenu.class);
                    if (currentProductMenu != null){
                        currentProductMenu.setId(child.getKey());
                        productMenuList.add(currentProductMenu);
                    }
                }
                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rvMenuList = findViewById(R.id.rvMenuList);
        rvMenuList.setAdapter(menuAdapter);
        rvMenuList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClickNewMenu(View view){
        Intent editMenuIntent = new Intent(this, EditMenuActivity.class);
        startActivityForResult(editMenuIntent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        menuAdapter.notifyDataSetChanged();
    }

}
