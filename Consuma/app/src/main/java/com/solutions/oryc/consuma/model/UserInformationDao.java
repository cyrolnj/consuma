package com.solutions.oryc.consuma.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.oryc.consuma.control.UserInformation;

public class UserInformationDao {

    private static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("userInformation/");

    public static void createUserInformation (UserInformation userInformation) {
        dbRef.child(userInformation.getUserId()).setValue(userInformation);
    }

    public static void updateUserInformation (UserInformation userInformation) {
        dbRef.child(userInformation.getUserId()).setValue(userInformation);
    }

    public static void addUserCredit (String userId, double credit) {
        final double localCredit = credit;
        DatabaseReference dbRefUserInformation = FirebaseDatabase.getInstance()
                                                                 .getReference("userInformation/" + userId);

        dbRefUserInformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                userInformation.setCurrentCredit(userInformation.getCurrentCredit() + localCredit);
                UserInformationDao.updateUserInformation(userInformation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
