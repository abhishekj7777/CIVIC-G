package com.example.myapplication;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account_setup_department extends AppCompatActivity {

    CircleImageView setupImage;
    EditText etName,etPhone,etDepartment;
    Button btnSave;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private Uri resultUri=null;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    String user_id,dep_name,dep_phone,dep_deartment;
    private  boolean isChanged=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_department);


        setupImage=findViewById(R.id.ivProfile_dep);
        etName=findViewById(R.id.etName_dep);
        etPhone=findViewById(R.id.etPhone_dep);
        etDepartment=findViewById(R.id.etDepartment);
        btnSave=findViewById(R.id.btnSave_dep);
        mAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.show();
        user_id=mAuth.getCurrentUser().getUid();
        mStorageRef= FirebaseStorage.getInstance().getReference();

        btnSave.setEnabled(false);


        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        String image=task.getResult().getString("image");
                        String name=task.getResult().getString("name");
                        String phone=task.getResult().getString("mobile_no");
                        String department=task.getResult().getString("department");
                        resultUri=Uri.parse(image);
                        etName.setText(name);
                        etPhone.setText(phone);
                        etDepartment.setText(department);
                        RequestOptions placeholderRequest=new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.df);
                        Glide.with(Account_setup_department.this)
                                .setDefaultRequestOptions(placeholderRequest)
                                .load(image).into(setupImage);
                        Toast.makeText(Account_setup_department.this, "Data exists", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Account_setup_department.this, "Data doesn't exists", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    String error=task.getException().getMessage();
                    Toast.makeText(Account_setup_department.this, "Error "+error, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                btnSave.setEnabled(true);
            }
        });

        etDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(Account_setup_department.this,etDepartment);
                popupMenu.getMenuInflater().inflate(R.menu.m3,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String  dep= (String) item.getTitle();
                        etDepartment.setText("Department: "+dep);
                        etDepartment.setTextColor(Color.BLACK);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.setMessage("Loading...");
//                progressDialog.show();
//                dep_name = etName.getText().toString().trim();
//                dep_phone = etPhone.getText().toString().trim();
//                dep_deartment = etDepartment.getText().toString().trim();
//                if (validate()) {
//                    if(isChanged) {
//                        user_id = mAuth.getCurrentUser().getUid();
//                        StorageReference image_path = mStorageRef.child("profile_images").child(user_id + ".jpg");
//                        image_path.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    progressDialog.dismiss();
//                                    storeFirestore(task,dep_name,dep_phone,dep_deartment);
//                                } else {
//                                    progressDialog.dismiss();
//                                    String error = task.getException().getMessage();
//                                    Toast.makeText(Account_setup_department.this, "Error: " + error, Toast.LENGTH_SHORT).show();
//                                } }
//
//                            private void storeFirestore(Task<UploadTask.TaskSnapshot> task, String depName, String depPhone, String depDeartment) {
//
//                            }
//                        } ) ;
//                    }else{
//                        storeFirestore(null,dep_name,dep_phone,dep_deartment);
//                    }
//                }
//            }
//
//            private void storeFirestore(Object o, String depName, String depPhone, String depDeartment) {
//            }
//        });
//


            }

        }
