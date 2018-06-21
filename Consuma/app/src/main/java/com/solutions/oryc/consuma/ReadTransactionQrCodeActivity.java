package com.solutions.oryc.consuma;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReadTransactionQrCodeActivity extends AppCompatActivity {

    private boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_transaction_qr_code);

        if (!initialized) {
            initialized = true;
            IntentIntegrator integrator = new IntentIntegrator(ReadTransactionQrCodeActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.initiateScan();
        } else {
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
                finish();
            } else {
                //Toast.makeText(this, "Scaneado: " + result.getContents(), Toast.LENGTH_LONG).show();
                Intent readTransactionIntent = new Intent(this, ReadTransactionActivity.class);
                readTransactionIntent.putExtra("transactionId", result.getContents());
                startActivity(readTransactionIntent);
                finish();
            }
        }
    }
}
