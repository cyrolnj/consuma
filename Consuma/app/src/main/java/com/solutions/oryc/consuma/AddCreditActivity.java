package com.solutions.oryc.consuma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.solutions.oryc.consuma.model.UserInformationDao;

public class AddCreditActivity extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private EditText txtCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit);

        txtCredit = findViewById(R.id.txtCredit);
    }

    public void onClickConfirmAddCredit(View view){
        Double.parseDouble(txtCredit.getText().toString());
        UserInformationDao.addUserCredit(user.getUid(), Double.parseDouble(txtCredit.getText().toString()));
        Toast.makeText(AddCreditActivity.this, "Crédito adicionado com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }
}
