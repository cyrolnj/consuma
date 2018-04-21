package com.solutions.oryc.consuma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.solutions.oryc.consuma.adapters.ProductMenuAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.model.ProductMenuDao;

public class MenuListActivity extends AppCompatActivity {

    private ProductMenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        RecyclerView rvMenuList = findViewById(R.id.rvMenuList);

        menuAdapter = new ProductMenuAdapter( ProductMenuDao.getList() );
        rvMenuList.setAdapter(menuAdapter);
        rvMenuList.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onClickNewMenu(View view){

        ProductMenuDao.setCurrentMenu("New menu1");

        Intent editMenuIntent = new Intent(this, EditMenuActivity.class);
        startActivityForResult(editMenuIntent, 200);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        menuAdapter.notifyDataSetChanged();
    }

}
