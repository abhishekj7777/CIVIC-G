package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    String current_user_id;
    private BottomNavigationView mainBottomNav;
    // SharedPreferences sharedPreferences = getSharedPreferences("MyP1", MODE_PRIVATE);
    String account_type, user_id;
    private Object Menu;








    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        mainBottomNav = findViewById(R.id.mainBottomNav);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();

//
//
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        account_type = task.getResult().getString("type");
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });






//     Add Button on homescreen if click then create new post

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewPost_Activity.class);
                startActivity(intent);
            }
        });





        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //case R.id.ivHome:
                   //     replace(homeFragment);
                    //    return true;
                   // case R.id.ivNotification:
                //        replace(notificationFragment);
                  //      return true;
                 //   case R.id.ivPerson:
                   //     Intent intent=new Intent(MainActivity.this, User_details.class);
                     //   startActivity(intent);
                       // return true;
                    default:
                        return false;

                } } } ); }





    //Create menu Button that is about,logout, account setting


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;

    }

    //select option code
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ivSearch) {
            Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.Logout) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to logout ?")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                            //  mAuth.signOut();
                        }
                    })
                    .setNegativeButton("NO", null)
                    .show();
        }
        if (item.getItemId()==R.id.accountSetting){
            if(account_type.equals("u")) {
                Intent intent = new Intent(MainActivity.this, Account_setup.class);
                startActivity(intent);
            }else if (account_type.equals("n")){
                Intent intent = new Intent(MainActivity.this, Account_setup_NG.class);
                startActivity(intent);
            }else if (account_type.equals("d")) {
                Intent intent = new Intent(MainActivity.this, Account_setup_department.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Acc"+account_type, Toast.LENGTH_SHORT).show();
            }

        }






        if (item.getItemId() == R.id.about) {
            //Toast.makeText(MainActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, About_Project.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } else {
            current_user_id = mAuth.getCurrentUser().getUid();
            SharedPreferences sharedPreferences = getSharedPreferences("MyP1", MODE_PRIVATE);
            final String account_type_1 = sharedPreferences.getString("Type", "");
            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            Toast.makeText(MainActivity.this, "acc" + account_type, Toast.LENGTH_SHORT).show();

                            if (account_type_1.equals("U")) {
                                Intent intent = new Intent(MainActivity.this, Account_setup.class);
                                startActivity(intent);
                            } else if (account_type_1.equals("N")) {
                                Intent intent = new Intent(MainActivity.this, Account_setup_NG.class);
                                startActivity(intent);
                            } else if (account_type_1.equals("D")) {
                                Intent intent = new Intent(MainActivity.this, Account_setup_department.class);
                                startActivity(intent);
                            }

                        }
                        //Comment kiya hu bhai
                        // replace(homeFragment);
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser == null) {
//            Intent intent = new Intent(MainActivity.this, Login.class);
//            startActivity(intent);
//        } else {
//            current_user_id = mAuth.getCurrentUser().getUid();
//          final String account_type_1 = sharedPreferences.getString("Type", "");
//       }
//    }}
//}



    private void replace(Fragment fragment){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer,fragment);
        fragmentTransaction.commit();
    }


    /**
     *
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Splash_end_Activity.class);
                        startActivity(intent);

                        // Finish the current activity to prevent going back to it
                        finish();
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }}


