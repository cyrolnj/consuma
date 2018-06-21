package com.solutions.oryc.consuma.control;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class Product implements Parcelable {

    public String name;
    public double price;
    public int quantity;

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getName());
        parcel.writeDouble(this.getPrice());
        parcel.writeInt(this.getQuantity());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            Product product = new Product();
            product.setName(in.readString());
            product.setPrice(in.readDouble());
            product.setQuantity(in.readInt());
            return product;
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
