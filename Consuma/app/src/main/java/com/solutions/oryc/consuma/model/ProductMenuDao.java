package com.solutions.oryc.consuma.model;

import com.solutions.oryc.consuma.control.ProductMenu;

import java.util.ArrayList;

/**
 * Created by 2001608 on 16/04/2018.
 */

public class ProductMenuDao {

    public static ArrayList<ProductMenu> getList(){
        ArrayList<ProductMenu> retorno = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            retorno.add(new ProductMenu("Cardápio"+i));
        }

        return retorno;
    }
}
