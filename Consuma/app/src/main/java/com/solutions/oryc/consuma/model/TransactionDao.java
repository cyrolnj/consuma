package com.solutions.oryc.consuma.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.control.ProductMenu;
import com.solutions.oryc.consuma.control.Transaction;
import com.solutions.oryc.consuma.control.UserInformation;

import java.util.concurrent.ThreadLocalRandom;

public class TransactionDao {

    private static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("transaction/");

    public static void createTransaction (Transaction transaction) {
        transaction.setId(dbRef.push().getKey());
        dbRef.child(transaction.getId()).setValue(transaction);
    }

    public static void confirmTransaction (Transaction transaction) {
        final Transaction localTransaction = transaction;
        DatabaseReference dbRefBuyerUserCredit = FirebaseDatabase.getInstance()
                                                                 .getReference("userInformation/" +
                                                                                   transaction.getBuyerUserId());
        dbRefBuyerUserCredit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation buyerUserInformation = dataSnapshot.getValue(UserInformation.class);
                buyerUserInformation.setCurrentCredit(buyerUserInformation.getCurrentCredit() - localTransaction.getTotal());
                UserInformationDao.updateUserInformation(buyerUserInformation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference dbRefSellerUserCredit = FirebaseDatabase.getInstance()
                                                                  .getReference("userInformation/" +
                                                                                    transaction.getSellerUserId());

        dbRefSellerUserCredit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation buyerUserInformation = dataSnapshot.getValue(UserInformation.class);
                buyerUserInformation.setCurrentCredit(buyerUserInformation.getCurrentCredit() + localTransaction.getTotal());
                UserInformationDao.updateUserInformation(buyerUserInformation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        transaction.setStatus(Transaction.TRANSACTION_PAYMENT_COMFIRMED);
        updateTransaction(transaction);
    }

    public static void updateTransaction (Transaction transaction) {
        dbRef.child(transaction.getId()).setValue(transaction);
    }

}
