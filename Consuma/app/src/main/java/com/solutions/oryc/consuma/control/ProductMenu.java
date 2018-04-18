package com.solutions.oryc.consuma.control;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenu {

    public String name;
    public ArrayList<Product> productList = new ArrayList<Product>();

    public ProductMenu(String name) {
        this.name = name;
    }

    public void addProduct (Product product) {
        productList.add(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getProductList() {
        return productList;
    }

    public void setProductList(ArrayList productList) {
        this.productList = productList;
    }
}
