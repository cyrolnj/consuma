package com.solutions.oryc.consuma.control;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Transaction implements Parcelable {

    public static final int NEW_TRANSACTION = 1;
    public static final int TRANSACTION_INITIALIZED = 1;
    public static final int TRANSACTION_WAITING_ATTENDENT = 2;
    public static final int TRANSACTION_IN_PROGRESS = 3;
    public static final int TRANSACTION_WAITING_PAYMENT = 4;
    public static final int TRANSACTION_PAYMENT_COMFIRMED = 5;
    public static final int TRANSACTION_CANCELED = 6;

    private String id;
    private String buyerUserId;
    private String sellerUserId;
    private int status;
    private double total;
    private ArrayList<Product> productList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(String sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<Product> getProductList() {
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
        parcel.writeString(this.getBuyerUserId());
        parcel.writeString(this.getSellerUserId());
        parcel.writeInt(this.getStatus());
        parcel.writeDouble(this.getTotal());
        parcel.writeList(this.getProductList());
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            Transaction transaction = new Transaction();
            transaction.setId(in.readString());
            transaction.setBuyerUserId(in.readString());
            transaction.setSellerUserId(in.readString());
            transaction.setStatus(in.readInt());
            transaction.setTotal(in.readDouble());
            transaction.setProductList(in.readArrayList(Product.class.getClassLoader()));
            return transaction;
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
