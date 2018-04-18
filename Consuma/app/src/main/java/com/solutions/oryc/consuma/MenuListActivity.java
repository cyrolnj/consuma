package com.solutions.oryc.consuma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class MenuListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        RecyclerView rvMenuList = findViewById(R.id.rvMenuList);

        ProductMenuAdapter menuAdapter= new ProductMenuAdapter( ProductMenuDao.getList() );
        rvMenuList.setAdapter(menuAdapter);
        rvMenuList.setLayoutManager(new LinearLayoutManager(this));

    }




}
