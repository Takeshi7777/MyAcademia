package com.taki.myacademia1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText FullName,Email,Password,RePassword;
    Button RegButton;
    TextView LoginLink;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullName    = findViewById(R.id.name);
        Email       = findViewById(R.id.email);
        Password    = findViewById(R.id.pass);
        RePassword  = findViewById(R.id.rePass);
        RegButton   = findViewById(R.id.regButton);
        LoginLink   = findViewById(R.id.signlink);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        final String repass = RePassword.getText().toString().trim();

        RegButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    Password.setError("Password is Required.");
                    return;
                }

                if(pass.length()<6){
                    Password.setError("Password must be greater than 6 characters.");
                    return;
                }

                if (repass.equals(pass)){
                    RePassword.setError("Passwords entered don't match.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //registering the user
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,"User Creation Failed."+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
