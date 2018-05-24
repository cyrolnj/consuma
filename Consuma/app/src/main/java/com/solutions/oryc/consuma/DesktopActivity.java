package com.solutions.oryc.consuma;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.solutions.oryc.consuma.control.ProductMenu;


public class DesktopActivity extends AppCompatActivity {

    private FirebaseUser logedInUser;
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
    }


    public void onClickReadMenu (View view) {
        final Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    public void onClickMenuList (View view) {
        Intent menuListIntent = new Intent(this, MenuListActivity.class);
        startActivity(menuListIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Scaneado: " + result.getContents(), Toast.LENGTH_LONG).show();

                Intent readProductMenuIntent = new Intent(this, ReadMenuActivity.class);
                readProductMenuIntent.putExtra("productMenuId", result.getContents());
                startActivityForResult(readProductMenuIntent, 200);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null) {
            this.logedInUser = user;
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }
    }

    public  void onClickLogout(View view){
        fbAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin(){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }


}
