package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityUserDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_details extends AppCompatActivity {

    CircleImageView setupImage;
    TextView tvName,tvSolvedPost,tvUnSolvedPost;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String user_id;
    private Uri resultUri=null;




     }