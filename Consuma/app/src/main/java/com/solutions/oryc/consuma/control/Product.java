package com.solutions.oryc.consuma.control;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class Product implements Parcelable {

    public String name;
    public float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getName());
        parcel.writeFloat(this.getPrice());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            Product retorno = new Product();
            retorno.setName(in.readString());
            retorno.setPrice(in.readFloat());
            return retorno;
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
