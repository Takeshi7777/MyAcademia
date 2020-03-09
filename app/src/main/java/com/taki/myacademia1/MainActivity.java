package com.taki.myacademia1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth    = FirebaseAuth.getInstance();
        final TextView text1  = findViewById(R.id.Hellotext);
        Button btn      = findViewById(R.id.sign_out);
        Button btn2     = findViewById(R.id.SignIn_main);

        FirebaseUser user = firebaseAuth.getCurrentUser();
/*
        if(firebaseAuth.getCurrentUser() != null){
            String DisplayName = user.getDisplayName();
            text1.setText(DisplayName);

        }
*/
        FirebaseDatabase user_db = FirebaseDatabase.getInstance();
        DatabaseReference user_ref= user_db.getReference().child("Users");
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                text1.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}
