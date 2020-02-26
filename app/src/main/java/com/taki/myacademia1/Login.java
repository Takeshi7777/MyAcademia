package com.taki.myacademia1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText Email, Password;
    Button signinbtn;
    TextView RegLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.signemail);
        Password = findViewById(R.id.signpass);
        signinbtn = findViewById(R.id.sign_inBtn);
        RegLink = findViewById(R.id.reg_link);

        firebaseAuth = FirebaseAuth.getInstance();

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = Email.getText().toString().trim();
                String pass1 = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email1)) {
                    Email.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(pass1)) {
                    Password.setError("Password is Required.");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email1, pass1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "User Signed-In.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "User Sign-In Failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        RegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });
    }
}
