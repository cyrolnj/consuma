package com.solutions.oryc.consuma.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenuDao {


    private static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("productMenu");

    public static ArrayList<ProductMenu> getList(){

        final ArrayList productMenuList = new ArrayList<ProductMenu>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children ) {
                    ProductMenu currentProductMenu = child.getValue(ProductMenu.class);
                    productMenuList.add(currentProductMenu);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return productMenuList;
    }

    public static void createProductMenu (ProductMenu productMenu) {
        productMenu.setId(dbRef.push().getKey());
        dbRef.child(productMenu.getId()).setValue(productMenu);
    }

    public static void updateProductMenu (ProductMenu productMenu) {
        dbRef.child(productMenu.getId()).setValue(productMenu);
    }

    public static void deleteProductMenu (ProductMenu productMenu) {
        dbRef.child(productMenu.getId()).removeValue();
    }

    public static void deleteProduct (ArrayList<Product> productList, int index) {
        productList.remove(index);
    }
}
