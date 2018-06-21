package com.solutions.oryc.consuma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.adapters.ProductAdapter;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.control.ShoppingCart;
import com.solutions.oryc.consuma.control.Transaction;
import com.solutions.oryc.consuma.model.TransactionDao;

import java.util.ArrayList;

public class ReadTransactionActivity extends AppCompatActivity {

    public final int READ_TRANSACTION_ACTIVITY = 4;
    private Transaction transaction = new Transaction();
    private DatabaseReference dbRef;
    private final ArrayList<Product> productList = new ArrayList<Product>();
    private ProductAdapter productAdapter = new ProductAdapter( productList, READ_TRANSACTION_ACTIVITY );
    private TextView vtxtTransactionTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_transaction);

        String transactionId = getIntent().getStringExtra("transactionId");

        dbRef = FirebaseDatabase.getInstance().getReference("transaction/" + transactionId);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                transaction = dataSnapshot.getValue(Transaction.class);
                vtxtTransactionTotal = findViewById(R.id.vtxtTransactionTotal);
                vtxtTransactionTotal.setText(String.valueOf(transaction.getTotal()));

                productList.clear();
                ArrayList<Product> localProductList = transaction.getProductList();
                for (Product product : localProductList) {
                    productList.add(product);
                }

                productAdapter.notifyDataSetChanged();

                switch(transaction.getStatus()) {
                    case (Transaction.TRANSACTION_INITIALIZED) : {
                        transaction.setStatus(Transaction.TRANSACTION_IN_PROGRESS);
                        TransactionDao.updateTransaction(transaction);
                        break;
                    }
                    case (Transaction.TRANSACTION_IN_PROGRESS) : {
                        break;
                    }

                    case (Transaction.TRANSACTION_WAITING_PAYMENT) : {
                        Toast.makeText(ReadTransactionActivity.this, "Aguardando confirmação do pagamento", Toast.LENGTH_LONG).show();
                        break;
                    }

                    case (Transaction.TRANSACTION_PAYMENT_COMFIRMED) : {
                        Toast.makeText(ReadTransactionActivity.this, "Pagamento confirmado", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }

                    case (Transaction.TRANSACTION_CANCELED) : {
                        Toast.makeText(ReadTransactionActivity.this, "Transação cancelada pelo comprador", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView rvProductList = findViewById(R.id.rvProductList);
        rvProductList.setAdapter(productAdapter);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onClickConfirmSell(View view) {
        transaction.setStatus(Transaction.TRANSACTION_WAITING_PAYMENT);
        TransactionDao.updateTransaction(transaction);
    }

    public void onClickCancelSell(View view) {
        transaction.setStatus(Transaction.TRANSACTION_CANCELED);
        TransactionDao.updateTransaction(transaction);
        Toast.makeText(ReadTransactionActivity.this, "Transação cancelada", Toast.LENGTH_LONG).show();
        finish();
    }

}
