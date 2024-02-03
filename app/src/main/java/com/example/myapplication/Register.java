package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class Register extends AppCompatActivity {


    public static final String TAG="TAG";
//    EditText mFullName,mEmail,mPassword,mphone;
//    TextView mloginbtn;
   // progressBar progressBAr;

    TextView tvRegister,tvLogin;
    EditText etEmail,etPassword,etConfirmPassword;
    Button btnRegister;
    private FirebaseAuth mAuth;
   // private ProgressDialog progressDialog;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        tvLogin = findViewById(R.id.tvLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPass_word);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

//       if(fAuth.getCurrentUser()!=null){
//           startActivity(new Intent(getApplicationContext(),MainActivity.class));
//          finish();
//      }


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirm_password = etConfirmPassword.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is Required");
                    return;

                }
                if (!password.equals(confirm_password)) {
                    etPassword.setError("Password are not matched");
                    return;

                }


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "OnFailure:Email not sent" + e.getMessage());

                                }
                            });





                            Toast.makeText(getApplicationContext(), "user Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("user").document(userID);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Email", etEmail);
                            user.put("Password", etPassword);
                            user.put("conPassword", etConfirmPassword);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Onsuccess:user profile is created for" + userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Onfailure:" + e.toString());
                                }


                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }}

