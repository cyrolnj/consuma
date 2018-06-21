package com.solutions.oryc.consuma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view) {

        Boolean error = false;
        Context context = getApplicationContext();
        CharSequence message = "";
        int duration = Toast.LENGTH_LONG;

        EditText txtEmail = this.findViewById(R.id.txtEmail);
        EditText txtPassword = this.findViewById(R.id.txtPassword);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (!isValidEmail(email)) {
            error = setEditTextError(txtEmail, "Email inválido");
        }

        if (!isValidPassword(password)) {
            error = setEditTextError(txtPassword, "Senha inválida");
        }

        if (!error) {

            //sendToMain(); //Comentar essa linha para ativar o login pelo Firebase
            //Descomentar esse bloco para ativar o login pelo Firebase

            //try to log in
                fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String message = "Login realizado com sucesso";
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        sendToMain();
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }

            });

        }

    }

    public void onClickCreateAccount (View view) {
        Intent createAccountIntent = new Intent(this, CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

    public final static boolean isValidEmail(String email) {
        try {
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        } catch (Exception e) {
            return false;
        }
    }

    public final static boolean isValidPassword(String password) {
        try {
            return (!TextUtils.isEmpty(password) && password.length() >= 8);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean setEditTextError(EditText editText, String message) {
        editText.setError(message);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null) {
            sendToMain();
        } else {

        }
    }

    private void sendToMain(){
        Intent desktopIntent = new Intent(this, DesktopActivity.class);
        startActivity(desktopIntent);
        finish();
    }
}




































