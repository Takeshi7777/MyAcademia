package com.taki.myacademia1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText FullName,Email,Password,RePassword;
    Button RegButton;
    TextView LoginLink;
    TextView Mobile;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    Spinner Stream;

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
        progressBar = findViewById(R.id.progressBar);
        Mobile      = findViewById(R.id.Mobile_num);
        Stream      = findViewById(R.id.spinner);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = firebaseAuth.getCurrentUser();
/*
            s.setVisibility(View.INVISIBLE);
            t.setVisibility(View.INVISIBLE);

        tech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (tech.isChecked()){
                    s.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.VISIBLE);
                }
            }
        });

        stud.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (stud.isChecked()){
                    s.setVisibility(View.VISIBLE);
                    t.setVisibility(View.INVISIBLE);
                }
            }
        });

*/

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        final String repass = RePassword.getText().toString().trim();
       // final String trepass = tRePassword.getText().toString().trim();

        ArrayAdapter<String> Myadap = new ArrayAdapter<String>(Register.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.streamdrop));
        Myadap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Stream.setAdapter(Myadap);


        RegButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Password.setError("Password is Required.");
                    return;
                }

                if (pass.length() < 6) {
                    Password.setError("Password must be greater than 6 characters.");
                    return;
                }

                if (pass.equals(repass)) {
                    RePassword.setError("Passwords entered don't match.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //registering the user
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder()
                                    .setDisplayName(FullName.toString())
                                    .build();
                            user1.updateProfile(profileChangeRequest);
                            String User_id = firebaseAuth.getUid();
                            FirebaseDatabase user_db = FirebaseDatabase.getInstance("https://myacademia-5c7df.firebaseio.com/");
                            DatabaseReference user_ref= user_db.getReference().child("Users");

                            String name= FullName.getText().toString().trim();
                            String email= Email.getText().toString().trim();
                            String mobile = Mobile.getText().toString().trim();
                            String stream = Stream.getSelectedItem().toString();

                            User adduser = new User(User_id,name,email,mobile,stream);

                            user_ref.setValue(adduser);

                           /* Map<String,Object> adduser= new HashMap<>();
                            adduser.put("Name", name);
                            adduser.put("Email",email);
                            adduser.put("Mobile",mobile);
                            adduser.put("Stream",stream);
                            user_ref.setValue(adduser);
                            */
                            finish();
                        } else {
                            Toast.makeText(Register.this, "User Creation Failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        LoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}