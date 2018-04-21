package com.solutions.oryc.consuma.model;

import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenuDao {

    private static ArrayList<ProductMenu> menuList = new ArrayList<>();
    private static ProductMenu currentMenu;

    public static ProductMenu getCurrentMenu() {
        return currentMenu;
    }

    public static ArrayList<ProductMenu> getList(){
        return menuList;
    }

    public static void addProductToCurrentMenu(Product product){
        currentMenu.addProduct(product);
    }

    public static void setCurrentMenu(String menuName){
        currentMenu = new ProductMenu(menuName);
    }

    public static void setCurrentMenuName(String menuName){
        currentMenu.setName(menuName);
    }

    public static void addMenu(ProductMenu productMenu){
        menuList.add(productMenu);
    }

}
