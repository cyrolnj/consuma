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

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void onClickCreateAccount(View view) {

        Boolean error = false;
        Context context = getApplicationContext();
        CharSequence message = "";
        int duration = Toast.LENGTH_LONG;

        EditText txtName = this.findViewById(R.id.txtName);
        EditText txtEmail = this.findViewById(R.id.txtEmail);
        EditText txtPassword = this.findViewById(R.id.txtPassword);
        EditText txtRepeatPassword = this.findViewById(R.id.txtRepeatPassword);

        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String repeatPassword = txtRepeatPassword.getText().toString();


        if (!isValidName(name)) {
            error = setEditTextError(txtName, "Informe seu nome");
        }

        if (!isValidEmail(email)) {
            error = setEditTextError(txtEmail, "Email inválido");
        }

        if (!isValidPassword(password)) {
            error = setEditTextError(txtPassword, "Senha inválida");
        }

        if (!isValidPassword(repeatPassword)) {
            error = setEditTextError(txtRepeatPassword, "Senha inválida");
        }

        if (isValidPassword(password) && isValidPassword(repeatPassword)) {
            if (!password.equals(repeatPassword)) {
                error = setEditTextError(txtRepeatPassword, "Senhas não coincidem");
            }
        }

        if (!error) {
            //try create account
            fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String message = "Cadastro realizado com sucesso";
                        Toast toast = Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG);
                        toast.show();
                        sendToMain();
                    } else {
                        String message = task.getException().getMessage();
                        Toast toast = Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });
        }

    }

    public final static boolean isValidName(String name) {
        try {
            return (!TextUtils.isEmpty(name));
        } catch (Exception e) {
            return false;
        }
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




































