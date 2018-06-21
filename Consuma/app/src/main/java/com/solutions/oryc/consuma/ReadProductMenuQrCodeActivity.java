package com.solutions.oryc.consuma;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReadProductMenuQrCodeActivity extends AppCompatActivity {

    private boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qr_code);

            if (!initialized) {
                initialized = true;
                IntentIntegrator integrator = new IntentIntegrator(ReadProductMenuQrCodeActivity.this);
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
                Intent readProductMenuIntent = new Intent(this, ReadMenuActivity.class);
                readProductMenuIntent.putExtra("productMenuId", result.getContents());
                startActivity(readProductMenuIntent);
                finish();
            }
        }
    }
}
