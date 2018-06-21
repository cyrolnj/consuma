package com.solutions.oryc.consuma.control;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ShoppingCart implements Parcelable {

    public String buyerUserId;
    public String sellerUserId;
    public ArrayList<Product> productList = new ArrayList<Product>();

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public void setSellerUserId(String sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public String getSellerUserId() {
        return sellerUserId;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void addProduct (Product product) {
        this.productList.add(product);
    }

    public double getTotal() {
        double total = 0;

        for (Product product:productList) {
            total = total + ( product.getPrice() * product.getQuantity() );
        }

        return total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getBuyerUserId());
        parcel.writeString(this.getSellerUserId());
        parcel.writeList(this.getProductList());
    }

    public static final Creator<ShoppingCart> CREATOR = new Creator<ShoppingCart>() {
        @Override
        public ShoppingCart createFromParcel(Parcel in) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setBuyerUserId(in.readString());
            shoppingCart.setSellerUserId(in.readString());
            shoppingCart.setProductList(in.readArrayList(Product.class.getClassLoader()));
            return shoppingCart;
        }

        @Override
        public ShoppingCart[] newArray(int size) {
            return new ShoppingCart[size];
        }
    };
}
