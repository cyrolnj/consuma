package com.solutions.oryc.consuma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.solutions.oryc.consuma.control.Product;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.control.Transaction;
import com.solutions.oryc.consuma.model.TransactionDao;

import java.util.ArrayList;

public class ShowTransactionQrCodeActivity extends AppCompatActivity {

    public final static int QRcodeWidth = 500 ;
    public final int TRANSACTION_INITIALIZED = 1;
    public final int TRANSACTION_WAITING_ATTENDENT = 2;
    public final int TRANSACTION_IN_PROGRESS = 3;
    public final int TRANSACTION_WAITING_PAYMENT = 4;
    public final int TRANSACTION_PAYMENT_COMFIRMED = 5;
    public final int TRANSACTION_CANCELED = 6;
    private Transaction transaction;
    private Bitmap bitmap;
    private ImageView imgTransactionCode;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transaction_qr_code);

        transaction = (Transaction) getIntent().getParcelableExtra("transaction");

        dbRef = FirebaseDatabase.getInstance().getReference("transaction/" + transaction.getId());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transaction = dataSnapshot.getValue(Transaction.class);

                switch(transaction.getStatus()) {
                    case (Transaction.TRANSACTION_INITIALIZED) : {
                        break;
                    }
                    case (Transaction.TRANSACTION_IN_PROGRESS) : {
                        Toast.makeText(ShowTransactionQrCodeActivity.this, "Aguardando confirmação da venda", Toast.LENGTH_LONG).show();
                        break;
                    }

                    case (Transaction.TRANSACTION_WAITING_PAYMENT) : {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowTransactionQrCodeActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Confirmar pagamento");
                        builder.setMessage("A venda foi confirmada pelo vendedor, deseja confirmar o pagamento?");
                        builder.setPositiveButton("Confirmar pagamento",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        transaction.setStatus(Transaction.TRANSACTION_PAYMENT_COMFIRMED);
                                        TransactionDao.confirmTransaction(transaction);
                                    }
                                });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                transaction.setStatus(Transaction.TRANSACTION_CANCELED);
                                TransactionDao.updateTransaction(transaction);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    }

                    case (Transaction.TRANSACTION_PAYMENT_COMFIRMED) : {
                        Toast.makeText(ShowTransactionQrCodeActivity.this, "Pagamento confirmado", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }

                    case (Transaction.TRANSACTION_CANCELED) : {
                        Toast.makeText(ShowTransactionQrCodeActivity.this, "Transação cancelada", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try {
            bitmap = TextToImageEncode(transaction.getId());
        } catch (WriterException e) {
            e.printStackTrace();
        }

        imgTransactionCode = findViewById(R.id.imgTransactionQrCode);
        imgTransactionCode.setImageBitmap(bitmap);

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public void onCancelTransaction(View view){
        transaction.setStatus(Transaction.TRANSACTION_CANCELED);
        TransactionDao.updateTransaction(transaction);
        Toast.makeText(ShowTransactionQrCodeActivity.this, "Transação cancelada", Toast.LENGTH_LONG).show();
        finish();
    }
}
