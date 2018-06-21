package com.solutions.oryc.consuma.control;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenu implements Parcelable {

    public String id;
    public String name;
    public String userId;
    public ArrayList<Product> productList = new ArrayList<Product>();

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void addProduct (Product product) {
        productList.add(product);
    }

    public void updateProduct (Product product, int position) { productList.set(position, product); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getId());
        parcel.writeString(this.getName());
        parcel.writeString(this.getUserId());
        parcel.writeList(this.getProductList());
    }

    public static final Creator<ProductMenu> CREATOR = new Creator<ProductMenu>() {
        @Override
        public ProductMenu createFromParcel(Parcel in) {
            ProductMenu productMenu = new ProductMenu();
            productMenu.setId(in.readString());
            productMenu.setName(in.readString());
            productMenu.setUserId(in.readString());
            productMenu.setProductList(in.readArrayList(Product.class.getClassLoader()));
            return productMenu;
        }

        @Override
        public ProductMenu[] newArray(int size) {
            return new ProductMenu[size];
        }
    };
}
