package com.solutions.oryc.consuma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.solutions.oryc.consuma.Interfaces.ProductOnItemClickListener;
import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ShoppingCart;
import com.solutions.oryc.consuma.control.Transaction;
import com.solutions.oryc.consuma.model.TransactionDao;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {


    public final int NEW_TRANSACTION = 1;
    public final int TRANSACTION_INITIALIZED = 1;
    public final int TRANSACTION_WAITING_ATTENDENT = 2;
    public final int TRANSACTION_IN_PROGRESS = 3;
    public final int TRANSACTION_WAITING_PAYMENT = 4;
    public final int TRANSACTION_PAYMENT_COMFIRMED = 5;
    public final int TRANSACTION_CANCELED = 6;
    public final int BACK_PRESSED = 8;
    public final int SHOP_ACTIVITY = 3;
    private ProductAdapter productAdapter;
    private ShoppingCart shoppingCart = new ShoppingCart();
    private Transaction transaction = new Transaction();
    private TextView vtxtShopTotal;
    Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        shoppingCart = (ShoppingCart) getIntent().getParcelableExtra("shoppingCart");
        vtxtShopTotal = findViewById(R.id.vtxtShopTotal);
        vtxtShopTotal.setText(String.valueOf(shoppingCart.getTotal()));

        if (shoppingCart != null) {

            RecyclerView rvProductList = findViewById(R.id.rvProductList);
            productAdapter = new ProductAdapter( shoppingCart.getProductList(), SHOP_ACTIVITY );
            rvProductList.setAdapter(productAdapter);
            rvProductList.setLayoutManager(new LinearLayoutManager(this));

            productAdapter.setOnItemClickListener(new ProductOnItemClickListener() {
                @Override
                public void onEditClick(int position) {
                }

                @Override
                public void onDeleteClick(int position) {
                    removeItem(position);
                    vtxtShopTotal.setText(String.valueOf(shoppingCart.getTotal()));
                }

                @Override
                public void onAddToCartClick(int position) {
                }

                @Override
                public void onIncreaseQuantityClick(int position) {
                    int quantity = shoppingCart.getProductList().get(position).getQuantity();
                    quantity = quantity + 1;
                    shoppingCart.getProductList().get(position).setQuantity(quantity);
                    vtxtShopTotal.setText(String.valueOf(shoppingCart.getTotal()));
                    productAdapter.notifyItemChanged(position);
                }

                @Override
                public void onDecreaseQuantityClick(int position) {
                    int quantity = shoppingCart.getProductList().get(position).getQuantity();
                    quantity = quantity - 1;
                    if (quantity <= 0) {
                        quantity = 1;
                    }
                    shoppingCart.getProductList().get(position).setQuantity(quantity);
                    vtxtShopTotal.setText(String.valueOf(shoppingCart.getTotal()));
                    productAdapter.notifyItemChanged(position);
                }
            });

        }

    }

    private void removeItem(int position) {
        shoppingCart.getProductList().remove(position);
        productAdapter.notifyItemRemoved(position);
        resultIntent = new Intent();
        resultIntent.putExtra("shoppingCart", shoppingCart);
        setResult(BACK_PRESSED, resultIntent);
    }

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    */

    public void onClickBuy(View view){
        transaction.setBuyerUserId(shoppingCart.getBuyerUserId());
        transaction.setSellerUserId(shoppingCart.getSellerUserId());
        transaction.setStatus(TRANSACTION_INITIALIZED);
        transaction.setTotal(shoppingCart.getTotal());
        transaction.setProductList(shoppingCart.getProductList());
        TransactionDao.createTransaction(transaction);
        Intent showTransactionQrCodeIntent = new Intent(this, ShowTransactionQrCodeActivity.class);
        showTransactionQrCodeIntent.putExtra("transaction", transaction);
        startActivity(showTransactionQrCodeIntent);
        finish();
        //startActivityForResult(showTransactionQrCodeIntent, NEW_TRANSACTION);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case (NEW_TRANSACTION) : {
                if (resultCode == TRANSACTION_CANCELED) {
                    //Extract the data returned from the child Activity.
                    Transaction transaction = (Transaction) data.getParcelableExtra("transaction");
                    transaction.setStatus(TRANSACTION_CANCELED);
                    TransactionDao.updateTransaction(transaction);
                }
                break;
            }

        }

        productAdapter.notifyDataSetChanged();
    }*/
}
